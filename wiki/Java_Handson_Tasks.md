# Java – Hands-on: Detailed Step-by-Step Tasks

> This document expands the **Java - Hands on** column from the *Kế hoạch chi tiết* sheet (`Study_Plan.xlsx`) into concrete, step-by-step tasks you can check off.
>
> - **Timeline:** 2026-07-07 → 2026-09-27 (12 weeks, ~82 study days)
> - **Each day contains:** *Learn* (context) → *Step-by-step tasks* (checklist) → *Ask AI* (self-check prompt) → *Definition of Done*.
> - **Repo convention:** put each day's code in its own package, e.g. `src/main/java/org/example/w03/day15/`. Keep every day's code so weekly "rewrite/audit" days can reference it.
> - **Toolbelt you'll install as you go:** JDK 21, Maven, IntelliJ, JOL, JMH, Eclipse MAT, JITWatch, async-profiler, hsdis, JMC, ByteBuddy, ASM.

---

## Week 1 — Java Core: Generics, Collections, Equality, Exceptions

### Day 2 · Tue · 2026-07-07 — Setup & Static Typing
**Learn:** JDK 21 + IntelliJ + Maven setup; static typing vs JS (primitives vs wrappers, autoboxing).

**Steps:**
1. [ ] Install JDK 21 (Temurin/Oracle). Verify: `java -version` shows `21`.
2. [ ] In IntelliJ: *New Project → Maven*, set Project SDK to 21. Confirm `pom.xml` has `<maven.compiler.release>21</maven.compiler.release>`.
3. [ ] Add Guava to `pom.xml`:
   ```xml
   <dependency>
     <groupId>com.google.guava</groupId>
     <artifactId>guava</artifactId>
     <version>33.2.1-jre</version>
   </dependency>
   ```
4. [ ] Reload Maven, then in `Main` call one Guava API (e.g. `com.google.common.collect.Lists.newArrayList("a","b")`) and print it. Run `Main`.
5. [ ] Write an autoboxing demo: assign `int` → `Integer` and back; loop-sum a `List<Integer>` to feel unboxing cost.
6. [ ] Reproduce the `Integer` cache trap: compare `Integer a=127, b=127` with `==` (true) vs `Integer c=128, d=128` with `==` (false), then with `.equals()`.
7. [ ] 🤖 **Ask AI:** "Compare the mental model of types in Java vs JS (primitives, wrappers, autoboxing)."

**Done:** Maven + Guava run; you can state 3 concrete differences between Java and JS typing.

### Day 3 · Wed · 2026-07-08 — Generics I
**Learn:** Type parameters, bounded types (`<T extends Comparable>`).

**Steps:**
1. [ ] Write `class Box<T>` with a private `T value`, plus `get()` / `set(T)`.
2. [ ] Write immutable `record Pair<K, V>(K key, V value)` (or a class with two `final` fields).
3. [ ] Write `static <T extends Comparable<T>> T max(List<T> list)` using `compareTo`; test with `Integer` and `String`.
4. [ ] Try to break type erasure on purpose: attempt `new T()` and `new T[10]` inside a generic class; note the compiler errors and *why*.
5. [ ] 🤖 **Ask AI:** "Where will type erasure bite me later?"

**Done:** `Box<T>` / `Pair<K,V>` compile-safe; you can explain type erasure.

### Day 4 · Thu · 2026-07-09 — Generics II (PECS)
**Learn:** Wildcards, PECS (`? extends` producer / `? super` consumer).

**Steps:**
1. [ ] Write `static <T> void copy(List<? super T> dest, List<? extends T> src)` that copies element by element.
2. [ ] Verify the rule: try to *add* to a `List<? extends T>` (fails) and try to *read a specific T* from `List<? super T>` (only `Object` comes out).
3. [ ] Call `copy` with `List<Number>` dest and `List<Integer>` src to see PECS in action.
4. [ ] 🤖 **Ask AI:** "Quiz me on producer vs consumer (PECS)."

**Done:** You can explain `? extends` vs `? super` without notes.

### Day 5 · Fri · 2026-07-10 — Collections & internals
**Learn:** ArrayList vs LinkedList; HashMap buckets & treeification.

**Steps:**
1. [ ] Benchmark 100k inserts at **head**, **middle**, **tail** for `ArrayList` vs `LinkedList` (use `System.nanoTime()` for a rough feel; proper JMH comes in Week 10).
2. [ ] Implement a toy `MyHashMap<K,V>`: bucket array, linked-list nodes, `put`, `get`, and `resize()` when load factor > 0.75.
3. [ ] Add JOL to inspect layout: `org.openjdk.jol:jol-core`, then `ClassLayout.parseInstance(map).toPrintable()`.
4. [ ] Draw (on paper/diagram) how a bucket converts to a red-black tree once it exceeds 8 entries (treeification).
5. [ ] 🤖 **Ask AI:** "How does my toy HashMap differ from the JDK's?"

**Done:** You can draw treeification; you have the 3-position insert benchmark.

### Day 6 · Sat · 2026-07-11 — equals/hashCode & sorting
**Learn:** equals/hashCode/immutability (EJ 10, 11, 17) + Comparable/Comparator.

**Steps:**
1. [ ] Create a value class (e.g. `Money(currency, amount)`) with a correct `equals` and `hashCode` (use `Objects.equals` / `Objects.hash`).
2. [ ] Make it immutable: all fields `final`, no setters.
3. [ ] Sort a list by 3 fields: `Comparator.comparing(A).thenComparing(B).thenComparing(C)`.
4. [ ] Break the contract: drop one field from `hashCode` only, put instances in a `HashMap`, and observe lookups failing.
5. [ ] 🤖 **Ask AI:** "Where did I break the equals/hashCode contract?"

**Done:** equals/hashCode obey the contract; multi-field sort is stable.

### Day 7 · Sun · 2026-07-12 — Review W1 + Exceptions
**Learn:** Exceptions (checked vs unchecked, try-with-resources, EJ 69–77).

**Steps:**
1. [ ] Build a small in-memory user DB (CRUD over a `Map<Long, User>`).
2. [ ] Define a custom exception hierarchy: base `UserException` → `UserNotFoundException`, `DuplicateUserException`.
3. [ ] Write one `AutoCloseable` resource and consume it via try-with-resources; confirm `close()` runs on both success and exception paths.
4. [ ] Decide per exception: checked vs unchecked, and justify each.
5. [ ] 🤖 **Ask AI:** Full review of Week 1 code.

**Done:** Custom exception hierarchy works; you pass your own review.

---

## Week 2 — Functional Java: Lambdas, Streams, Records, NIO.2, Build

### Day 8 · Mon · 2026-07-13 — Lambdas & functional interfaces
**Learn:** Lambdas, method references, `Function/Predicate/Consumer/Supplier`.

**Steps:**
1. [ ] Find/write 10 anonymous-class usages and convert each to a lambda.
2. [ ] Compose functions: build `f.andThen(g)` and `f.compose(g)`, and confirm order differs.
3. [ ] Combine predicates: `p1.and(p2).or(p3).negate()`.
4. [ ] Use all 4 method-reference kinds: static (`Integer::parseInt`), bound instance (`str::length`), unbound instance (`String::length`), constructor (`ArrayList::new`).
5. [ ] 🤖 **Ask AI:** "Where do my lambdas cause overload ambiguity?"

**Done:** 10 anon classes → lambdas; you use andThen/compose correctly.

### Day 9 · Tue · 2026-07-14 — Stream API I
**Learn:** map/filter/collect/reduce; lazy vs eager.

**Steps:**
1. [ ] Convert 5 `for` loops into streams (`map`/`filter`/`collect(toList())`).
2. [ ] Prove laziness: insert `.peek(System.out::println)` and observe nothing prints until a terminal op runs.
3. [ ] Use `reduce` for one aggregation (sum or string concat) and note the identity + accumulator.
4. [ ] 🤖 **Ask AI:** "Where do streams hurt readability here?"

**Done:** 3/5 loops become clean streams; you can explain lazy vs eager.

### Day 10 · Wed · 2026-07-15 — Stream API II + Optional
**Learn:** groupingBy/partitioningBy/downstream collectors + Optional.

**Steps:**
1. [ ] Build a sales report: `groupingBy(Sale::region, groupingBy(Sale::product, summingDouble(Sale::amount)))`.
2. [ ] Use `partitioningBy` for one boolean split (e.g. above/below target).
3. [ ] Refactor 5 null checks into `Optional` chains (`map`/`filter`/`orElseGet`) — never call `.get()` unguarded.
4. [ ] 🤖 **Ask AI:** "Where am I overusing Optional?"

**Done:** groupingBy report runs; 5 null checks removed correctly.

### Day 11 · Thu · 2026-07-16 — Records, sealed, pattern matching
**Learn:** Records + sealed classes + pattern matching (switch, record patterns).

**Steps:**
1. [ ] Define `sealed interface Result<S, E> permits Success, Error` with `record Success<S>(S value)` and `record Error<E>(E error)`.
2. [ ] Write an exhaustive `switch` over `Result` with **no** `default`; let the compiler enforce completeness.
3. [ ] Use record patterns to destructure: `case Success(var v) -> ...`.
4. [ ] Add a new permitted subtype and watch the switch fail to compile (exhaustiveness).
5. [ ] 🤖 **Ask AI:** "Quiz me on switch exhaustiveness."

**Done:** `Result<S,E>` via sealed + exhaustive switch.

### Day 12 · Fri · 2026-07-17 — NIO.2, text blocks, var
**Learn:** NIO.2 (Path/Files/Files.lines) + text blocks + var.

**Steps:**
1. [ ] Write a CSV/log reader using `Files.lines(path)` inside try-with-resources (the stream must be closed).
2. [ ] Parse each line into a record; collect to a list.
3. [ ] Use a text block (`"""..."""`) for a multi-line report template.
4. [ ] Use `var` for obvious local types; note where it hurts readability.
5. [ ] 🤖 **Ask AI:** "Find the resource leak in my file-reading code."

**Done:** `Files.lines` reader leaks nothing.

### Day 13 · Sat · 2026-07-18 — Build & packaging
**Learn:** Maven vs Gradle, dependency scopes, runnable (fat) JAR.

**Steps:**
1. [ ] Add `maven-shade-plugin` (or `maven-assembly-plugin`) and set the `Main-Class` manifest entry.
2. [ ] Run `mvn clean package`, then `java -jar target/<app>.jar` — it must run.
3. [ ] Write down the meaning of scopes: `compile`, `provided`, `runtime`, `test`.
4. [ ] List the default Maven lifecycle phases (validate → compile → test → package → verify → install → deploy).
5. [ ] 🤖 **Ask AI:** "What does the Maven lifecycle consist of?"

**Done:** Fat JAR runs via `java -jar`; you can name 8 Maven phases.

### Day 14 · Sun · 2026-07-19 — Review W2
**Learn:** Consolidate Week 2.

**Steps:**
1. [ ] Rewrite the Week 1 user DB using streams + records + Optional.
2. [ ] 🤖 **Ask AI:** Full review of the rewrite.

**Done:** User DB rewritten with stream + record + Optional; passes review.

---

## Week 3 — Concurrency foundations: Threads, JMM, locks, wait/notify

### Day 15 · Mon · 2026-07-20 — Threads & Runnable
**Learn:** Threads vs Runnable; lifecycle (start/sleep/join/interrupt).

**Steps:**
1. [ ] Print 1–10 from 3 threads, **two ways**: (a) `extends Thread`, (b) `implements Runnable`.
2. [ ] Use `join()` on the main thread to wait for all three.
3. [ ] Add a sleep, then `interrupt()` one thread and handle `InterruptedException` cleanly (restore the flag).
4. [ ] 🤖 **Ask AI:** "Why is separating the task from the thread better?"

**Done:** Print 1–10 two ways; you can argue task-vs-thread separation.

### Day 16 · Tue · 2026-07-21 — Java Memory Model
**Learn:** JMM + happens-before rules.

**Steps:**
1. [ ] Write one code example for each of the 5 happens-before rules: program order, monitor lock, `volatile`, thread `start()`, thread `join()`.
2. [ ] Annotate each with what is guaranteed visible and why.
3. [ ] 🤖 **Ask AI:** "Quiz me on happens-before edge cases."

**Done:** You can explain 5 happens-before rules with code.

### Day 17 · Wed · 2026-07-22 — synchronized & volatile
**Learn:** synchronized + volatile + visibility; atomicity vs visibility.

**Steps:**
1. [ ] Reproduce a race: two threads incrementing a shared `int count` 100k times each; final value < 200k.
2. [ ] Try to "fix" with `volatile` — show it still fails because `count++` is not atomic.
3. [ ] Fix properly with `synchronized` (or `AtomicInteger`).
4. [ ] 🤖 **Ask AI:** "When are volatile / synchronized NOT enough?"

**Done:** You reproduce + fix one race; you know when volatile is insufficient.

### Day 18 · Thu · 2026-07-23 — Visibility, reordering, DCL
**Learn:** Deep visibility & reordering; double-checked locking done right/wrong.

**Steps:**
1. [ ] Write a visibility bug: a non-`volatile` `boolean running` flag that a worker loop never sees flipped; watch it hang.
2. [ ] Fix by making the flag `volatile`.
3. [ ] Write a **wrong** DCL singleton (no `volatile` on the instance) then fix it (add `volatile`); explain the partial-construction hazard.
4. [ ] 🤖 **Ask AI:** "Which JMM guarantee applies here?"

**Done:** You wrote a broken DCL then fixed it; you cite the JMM guarantee.

### Day 19 · Fri · 2026-07-24 — Deadlock & lock ordering
**Learn:** Deadlock/livelock/starvation; lock ordering; wait/notify.

**Steps:**
1. [ ] Create a classic deadlock: two locks acquired in opposite order by two threads.
2. [ ] Confirm the deadlock with `jstack <pid>` (look for "Found one Java-level deadlock").
3. [ ] Fix by imposing a global lock ordering (always lock in a consistent order).
4. [ ] 🤖 **Ask AI:** Review the fix.

**Done:** You create a deadlock then fix it via lock ordering.

### Day 20 · Sat · 2026-07-25 — wait/notify & bounded buffer
**Learn:** wait/notify → bounded buffer; intro to BlockingQueue.

**Steps:**
1. [ ] Implement a bounded buffer with `wait()`/`notifyAll()` (producer/consumer, capacity N). Guard with a `while` condition, not `if`.
2. [ ] Rewrite the same buffer using `ArrayBlockingQueue`.
3. [ ] Compare correctness/complexity of the two versions.
4. [ ] 🤖 **Ask AI:** "Compare my implementation to ArrayBlockingQueue."

**Done:** wait/notify buffer is correct; compared to `ArrayBlockingQueue`.

### Day 21 · Sun · 2026-07-26 — Review W3
**Steps:**
1. [ ] Predict the output of 5 thread-coordination scenarios, then run them to verify.
2. [ ] 🤖 **Ask AI:** Explain where your predictions were wrong.

**Done:** Predict 5 thread scenarios correctly, then verify.

---

## Week 4 — Concurrency utilities: Executors, Futures, CompletableFuture

### Day 22 · Mon · 2026-07-27 — ExecutorService & thread pools
**Learn:** ExecutorService + pools (Fixed/Cached/Scheduled); correct shutdown.

**Steps:**
1. [ ] Refactor Week 3 threads to `Executors.newFixedThreadPool(n)`.
2. [ ] Shut down correctly: `shutdown()` → `awaitTermination(timeout)` → `shutdownNow()` fallback, and re-interrupt.
3. [ ] Try `newCachedThreadPool` under a burst and note the unbounded-thread risk.
4. [ ] 🤖 **Ask AI:** "When is CachedThreadPool dangerous?"

**Done:** Refactored to FixedThreadPool with correct shutdown.

### Day 23 · Tue · 2026-07-28 — Callable & Future
**Learn:** Callable + Future + get(timeout) + cancellation.

**Steps:**
1. [ ] Parallel-sum a large list by splitting into chunks, each a `Callable<Long>` submitted for a `Future`.
2. [ ] Combine partial results; use `get(timeout, TimeUnit)` and handle `TimeoutException`.
3. [ ] Cancel a slow task with `cancel(true)` and verify it stops.
4. [ ] 🤖 **Ask AI:** Review error/cancel handling.

**Done:** Parallel sum + get(timeout) + clean cancellation.

### Day 24 · Wed · 2026-07-29 — CompletableFuture basics
**Learn:** `supplyAsync`/`thenApply`/`thenCompose`/`thenCombine`.

**Steps:**
1. [ ] Build an async pipeline: `supplyAsync(fetch).thenApply(transform).thenCompose(aggregate)`.
2. [ ] Demonstrate the difference: `thenApply` (map) vs `thenCompose` (flatMap) vs `thenCombine` (join two CFs).
3. [ ] 🤖 **Ask AI:** "Is my exception handling sufficient here?"

**Done:** fetch→transform→aggregate pipeline runs asynchronously.

### Day 25 · Thu · 2026-07-30 — CompletableFuture advanced
**Learn:** `exceptionally`/`handle`/`allOf`/`anyOf`; custom executor.

**Steps:**
1. [ ] Fire 3 simulated API calls (sleep-based) in parallel; join with `allOf(...).thenApply(...)`.
2. [ ] Handle partial failure per-CF with `exceptionally` / `handle` so one failure doesn't sink the batch.
3. [ ] Run them on a custom `Executor` instead of the common pool.
4. [ ] 🤖 **Ask AI:** Review.

**Done:** 3 parallel API calls + partial-failure handling.

### Day 26 · Fri · 2026-07-31 — Concurrent collections & atomics
**Learn:** ConcurrentHashMap, CopyOnWrite, Atomic*, LongAdder.

**Steps:**
1. [ ] Build a thread-safe counter **3 ways**: `synchronized`, `AtomicLong`, `LongAdder`.
2. [ ] Benchmark all three under high thread contention.
3. [ ] Note when `ConcurrentHashMap`/`CopyOnWriteArrayList` fit vs not.
4. [ ] 🤖 **Ask AI:** "What are the trade-offs of each?"

**Done:** Counter 3 ways + benchmark, with trade-offs stated.

### Day 27 · Sat · 2026-08-01 — ThreadPoolExecutor internals
**Learn:** core/max/queue/rejection; worker-pool pattern.

**Steps:**
1. [ ] Construct a raw `ThreadPoolExecutor` with tuned corePoolSize/maxPoolSize/`ArrayBlockingQueue` for a bursty load.
2. [ ] Drive it until rejection occurs; try each `RejectedExecutionHandler` (Abort/CallerRuns/Discard/DiscardOldest).
3. [ ] 🤖 **Ask AI:** "When does this pool configuration fail?"

**Done:** Tuned pool for burst; observed rejection occurring.

### Day 28 · Sun · 2026-08-02 — Review W4
**Steps:**
1. [ ] Build a parallel file processor: read many files, aggregate results with `CompletableFuture.allOf`.
2. [ ] 🤖 **Ask AI:** Full review.

**Done:** Parallel file processor (CF aggregate) runs correctly.

---

## Week 5 — Concurrency capstone: AQS, advanced locks, lock-free, Loom

### Day 29 · Mon · 2026-08-03 — AbstractQueuedSynchronizer (AQS)
**Learn:** How ReentrantLock/Semaphore/Latch are built on AQS.

**Steps:**
1. [ ] Read AQS source: understand `state`, the CLH queue, `acquire`/`release`.
2. [ ] Build a simple `CountDownLatch` on AQS by implementing `tryAcquireShared`/`tryReleaseShared`.
3. [ ] Test it: N workers block until count reaches 0.
4. [ ] 🤖 **Ask AI:** "How do state + CAS work inside AQS?"

**Done:** A latch built on AQS; you can explain state + CAS.

### Day 30 · Tue · 2026-08-04 — Advanced locks
**Learn:** ReentrantLock vs synchronized, fairness, Condition; ReadWriteLock; StampedLock optimistic.

**Steps:**
1. [ ] Take a `synchronized` cache and convert reads to `StampedLock` optimistic reads (`tryOptimisticRead` → `validate` → fallback to read lock).
2. [ ] Measure read throughput before/after under read-heavy load.
3. [ ] 🤖 **Ask AI:** "When do optimistic reads lose?"

**Done:** Cache moved to StampedLock optimistic; you know when it loses.

### Day 31 · Wed · 2026-08-05 — Lock-free & CAS
**Learn:** AtomicInteger internals; compareAndSet; ABA; Treiber stack.

**Steps:**
1. [ ] Implement a lock-free Treiber stack: `push`/`pop` via `AtomicReference.compareAndSet` on the head.
2. [ ] Stress-test with many threads; assert no lost/duplicated elements.
3. [ ] Identify exactly where an ABA problem could occur (and how `AtomicStampedReference` fixes it).
4. [ ] 🤖 **Ask AI:** "Where does ABA bite?"

**Done:** Lock-free stack passes multi-thread stress test; ABA identified.

### Day 32 · Thu · 2026-08-06 — VarHandles & memory fences
**Learn:** VarHandles + fences (acquire/release/opaque/fullFence).

**Steps:**
1. [ ] Replace one `Atomic*` field with a `VarHandle` (`getAcquire`/`setRelease`/`compareAndSet`).
2. [ ] Reason about the minimum fence needed for each access; document it.
3. [ ] 🤖 **Ask AI:** Verify the memory ordering.

**Done:** One Atomic replaced by VarHandle with the correct fence chosen.

### Day 33 · Fri · 2026-08-07 — False sharing
**Learn:** False sharing + cache lines + `@Contended`.

**Steps:**
1. [ ] Write a JMH benchmark where two hot counters sit in the same cache line (false sharing).
2. [ ] Fix with padding or `@jdk.internal.vm.annotation.Contended` (run with `-XX:-RestrictContended`).
3. [ ] Compare throughput before/after.
4. [ ] 🤖 **Ask AI:** Interpret the benchmark numbers.

**Done:** False-sharing benchmark fixed via padding; you can read the numbers.

### Day 34 · Sat · 2026-08-08 — Virtual threads & structured concurrency
**Learn:** Loom, Java 21 virtual threads; scoped values.

**Steps:**
1. [ ] Rewrite an Executor task using `Executors.newVirtualThreadPerTaskExecutor()`.
2. [ ] Compare throughput vs platform threads on many blocking tasks (e.g. simulated I/O sleeps).
3. [ ] Try `StructuredTaskScope` to fan-out/fan-in with cancellation.
4. [ ] 🤖 **Ask AI:** "When should I NOT use virtual threads?"

**Done:** Rewritten with virtual threads; you can state when NOT to use them.

### Day 35 · Sun · 2026-08-09 — Review W5 (capstone)
**Steps:**
1. [ ] Build one of: a small rate limiter (token bucket) or a small work-stealing pool.
2. [ ] 🤖 **Ask AI:** Full review + list 3 concurrency anti-patterns.

**Done:** Rate limiter / work-stealing runs; you can name 3 anti-patterns.

---

## Week 6 — JVM: Memory layout, object model, references, OOM, heap dumps

### Day 36 · Mon · 2026-08-10 — Runtime data areas
**Learn:** Stack vs heap vs metaspace, PC register, native stack.

**Steps:**
1. [ ] Take a specific small program and diagram where each thing lives: locals (stack frame), objects (heap), class metadata (metaspace), statics.
2. [ ] Label the per-thread PC register and native stack.
3. [ ] 🤖 **Ask AI:** "Where did I get the layout wrong?"

**Done:** Correct diagram of stack/heap/metaspace/PC register.

### Day 37 · Tue · 2026-08-11 — Heap regions
**Learn:** Young (Eden/Survivor), Old, Metaspace; promotion & aging.

**Steps:**
1. [ ] Diagram the regions and the object path: Eden → Survivor (S0/S1) → Old.
2. [ ] Explain the aging threshold (`-XX:MaxTenuringThreshold`) and when premature promotion happens.
3. [ ] 🤖 **Ask AI:** Quiz me on promotion & aging.

**Done:** Young/Old diagram + explanation of promotion & aging.

### Day 38 · Wed · 2026-08-12 — Object layout (JOL)
**Learn:** Header/mark word, compressed oops, alignment; JOL.

**Steps:**
1. [ ] Use JOL: `System.out.println(ClassLayout.parseInstance(obj).toPrintable())` for a few objects.
2. [ ] Identify the mark word, klass pointer, fields, and padding.
3. [ ] Explain why a 12-byte object becomes 16 bytes (8-byte alignment).
4. [ ] Observe compressed oops on/off (`-XX:-UseCompressedOops`).
5. [ ] 🤖 **Ask AI:** "Why did 12 bytes become 16?"

**Done:** JOL sizes measured; padding 12→16 explained.

### Day 39 · Thu · 2026-08-13 — Reference types
**Learn:** Strong/Soft/Weak/Phantom + ReferenceQueue.

**Steps:**
1. [ ] Build a cache using `WeakReference` and one using `SoftReference`; force GC and observe when entries clear.
2. [ ] Register a `ReferenceQueue` and drain cleared references (like `WeakHashMap` does).
3. [ ] 🤖 **Ask AI:** "When is a WeakReference the wrong choice?"

**Done:** Weak/Soft cache works; you can say when Weak is wrong.

### Day 40 · Fri · 2026-08-14 — OutOfMemoryError variants
**Learn:** Heap, Metaspace, GC overhead, Direct buffer OOM.

**Steps:**
1. [ ] Trigger **Java heap space** OOM: keep adding to a growing list.
2. [ ] Trigger **Metaspace** OOM: generate classes dynamically in a loop.
3. [ ] Trigger **GC overhead limit exceeded**: heavy allocation churn with tiny heap.
4. [ ] Trigger **Direct buffer memory** OOM: allocate many `ByteBuffer.allocateDirect`.
5. [ ] Record the distinctive log message for each.
6. [ ] 🤖 **Ask AI:** "How do I tell these OOMs apart from the logs?"

**Done:** All 4 OOMs reproduced; distinguishable by log.

### Day 41 · Sat · 2026-08-15 — Heap dump & MAT
**Learn:** Capture (jmap/JFR) + read in MAT (dominator tree, retained heap).

**Steps:**
1. [ ] Create a leak (e.g. a static collection that never releases entries).
2. [ ] Capture a dump: `jmap -dump:live,format=b,file=heap.hprof <pid>` (or `-XX:+HeapDumpOnOutOfMemoryError`).
3. [ ] Open in Eclipse MAT; use the dominator tree + retained heap to find the leak suspect.
4. [ ] 🤖 **Ask AI:** Help reading retained heap.

**Done:** Leak found in MAT (dominator/retained heap).

### Day 42 · Sun · 2026-08-16 — Review W6
**Steps:**
1. [ ] Write a one-pager: "from `new Object()` to GC".
2. [ ] Reproduce heap OOM + metaspace OOM again from memory.
3. [ ] 🤖 **Ask AI:** Verify the write-up.

**Done:** One-pager new→GC; 2 OOMs reproduced.

---

## Week 7 — GC: reachability, collectors, G1, ZGC, tuning, troubleshooting

### Day 43 · Mon · 2026-08-17 — GC fundamentals
**Learn:** Reachability, mark-sweep-compact, generational hypothesis.

**Steps:**
1. [ ] Explain the generational hypothesis (most objects die young) with a concrete example.
2. [ ] Give a case where it's FALSE (long-lived caches, object pools).
3. [ ] 🤖 **Ask AI:** Challenge my reasoning.

**Done:** Generational hypothesis explained + when it's false.

### Day 44 · Tue · 2026-08-18 — The collectors
**Learn:** Serial/Parallel/CMS(deprecated)/G1 — throughput vs latency.

**Steps:**
1. [ ] Build a comparison table of 4 GCs by use case: throughput vs latency, heap size sweet spot, flag to enable.
2. [ ] 🤖 **Ask AI:** Verify the table.

**Done:** 4-GC comparison table (verified against docs).

### Day 45 · Wed · 2026-08-19 — G1 deep dive
**Learn:** Regions, humongous objects, RSet, mixed collections, pause target.

**Steps:**
1. [ ] Run an app with `-Xlog:gc*:file=gc.log:time,uptime,level,tags`.
2. [ ] Read each G1 phase in the log: young collection, concurrent mark, mixed collection.
3. [ ] Identify humongous allocations if any.
4. [ ] 🤖 **Ask AI:** "Which phase costs the most?"

**Done:** Each G1 phase read from the log; costliest identified.

### Day 46 · Thu · 2026-08-20 — Low-pause GC
**Learn:** ZGC & Shenandoah (colored pointers/load barrier, concurrent compaction).

**Steps:**
1. [ ] Run the same app with G1, then ZGC (`-XX:+UseZGC`), same workload.
2. [ ] Compare pause times from the GC logs.
3. [ ] Explain the load barrier and concurrent compaction at a high level.
4. [ ] 🤖 **Ask AI:** Interpret + explain the load barrier.

**Done:** G1 vs ZGC pauses compared; load barrier explained.

### Day 47 · Fri · 2026-08-21 — GC tuning
**Learn:** Heap sizing, pause vs throughput flags, allocation rate.

**Steps:**
1. [ ] Tune an app to a target pause < 100ms using `-XX:MaxGCPauseMillis`, `-Xms`/`-Xmx`.
2. [ ] Measure allocation rate from the logs and reduce it if possible.
3. [ ] Identify conflicting flags (e.g. fixing throughput vs pause).
4. [ ] 🤖 **Ask AI:** "Which flags conflict?"

**Done:** App tuned to pause <100ms; conflicting flags identified.

### Day 48 · Sat · 2026-08-22 — GC troubleshooting
**Learn:** Reading GC logs; spotting leaks / allocation churn.

**Steps:**
1. [ ] Write a slow memory leak; observe heap-after-full-GC trending upward in the log.
2. [ ] Confirm with a heap dump in MAT.
3. [ ] 🤖 **Ask AI:** Help with the analysis.

**Done:** Leak found via GC log + heap dump.

### Day 49 · Sun · 2026-08-23 — Review W7
**Steps:**
1. [ ] Build a decision tree: "which workload → which GC".
2. [ ] 🤖 **Ask AI:** Challenge me to defend the choices.

**Done:** workload→GC decision tree; you defend your choices.

---

## Week 8 — Bytecode & instrumentation: class file, ASM, ByteBuddy, agents

### Day 50 · Mon · 2026-08-24 — Class file anatomy
**Learn:** Constant pool, `javap -v`, verification.

**Steps:**
1. [ ] Write `Hello.java`, compile, then run `javap -v Hello`.
2. [ ] Walk the constant pool entries (UTF8, Class, Methodref, NameAndType…) and map them.
3. [ ] Note what bytecode verification checks at load time.
4. [ ] 🤖 **Ask AI:** Explain the constant pool.

**Done:** Full constant pool of one class read; verification explained.

### Day 51 · Tue · 2026-08-25 — Bytecode & invoke*
**Learn:** Stack machine, local vars, invoke (virtual/static/special/interface/dynamic).

**Steps:**
1. [ ] Write code that triggers all 5 invocation kinds (instance call, static call, `super`/private, interface call, lambda).
2. [ ] Read `javap -c` and map each call to `invokevirtual`/`invokestatic`/`invokespecial`/`invokeinterface`/`invokedynamic`.
3. [ ] 🤖 **Ask AI:** "When is each invoke used?"

**Done:** 5 invoke kinds distinguished via bytecode.

### Day 52 · Wed · 2026-08-26 — ASM
**Learn:** Generate & transform classes at the bytecode level.

**Steps:**
1. [ ] Add the ASM dependency; write a `ClassVisitor` + `MethodVisitor` that injects a log call at method entry.
2. [ ] Use `COMPUTE_FRAMES` so the stack-map frames are valid.
3. [ ] Verify the transformed class loads and runs.
4. [ ] 🤖 **Ask AI:** "Is my stack-frame map correct?"

**Done:** ASM injects entry log; stack-map frames valid.

### Day 53 · Thu · 2026-08-27 — ByteBuddy
**Learn:** High-level codegen/interception.

**Steps:**
1. [ ] Add ByteBuddy; create a proxy/subclass that times every method call (`@Advice.OnMethodEnter/OnMethodExit` or `MethodDelegation`).
2. [ ] Print method name + elapsed nanos.
3. [ ] 🤖 **Ask AI:** Review.

**Done:** Proxy times each method call.

### Day 54 · Fri · 2026-08-28 — Java agents
**Learn:** premain, Instrumentation API, retransformClasses.

**Steps:**
1. [ ] Write a `premain(String args, Instrumentation inst)` agent that rewrites one method at load time (via ByteBuddy `AgentBuilder` or a `ClassFileTransformer`).
2. [ ] Package with `Premain-Class` in the manifest; run with `-javaagent:agent.jar`.
3. [ ] Use `retransformClasses` on an already-loaded class; contrast with `redefineClasses`.
4. [ ] 🤖 **Ask AI:** "How do retransform and redefine differ?"

**Done:** `-javaagent` rewrites a method at load; retransform vs redefine understood.

### Day 55 · Sat · 2026-08-29 — invokedynamic & LambdaMetafactory
**Learn:** How lambdas/records actually compile.

**Steps:**
1. [ ] `javap -c -p` a class containing a lambda; find the `invokedynamic` and its bootstrap method.
2. [ ] Explain how `LambdaMetafactory` spins the implementation class at runtime.
3. [ ] 🤖 **Ask AI:** Verify.

**Done:** Bootstrap method of a lambda explained.

### Day 56 · Sun · 2026-08-30 — Review W8
**Steps:**
1. [ ] Build a small profiling agent that counts method calls using ByteBuddy.
2. [ ] 🤖 **Ask AI:** Full review.

**Done:** Method-call-counting agent runs.

---

## Week 9 — JIT: compilation tiers, inlining, escape analysis, deopt, assembly

### Day 57 · Mon · 2026-08-31 — Compilation tiers
**Learn:** Interpreter → C1 → C2, tiered, code cache.

**Steps:**
1. [ ] Run a hot method with `-XX:+PrintCompilation`.
2. [ ] Identify compilation levels 0–4 in the output; note when a method escalates.
3. [ ] 🤖 **Ask AI:** "Explain the compilation levels from this log."

**Done:** interpreter→C1→C2 explained from the log.

### Day 58 · Tue · 2026-09-01 — Inlining
**Learn:** Thresholds, MaxInlineSize; mono/bi/megamorphic call sites.

**Steps:**
1. [ ] Observe inlining with `-XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining`.
2. [ ] Start with a monomorphic call site (1 impl), then add many implementations to make it megamorphic; watch inlining disappear.
3. [ ] 🤖 **Ask AI:** "Why did inlining stop?"

**Done:** Inlining loss observed when mono→megamorphic.

### Day 59 · Wed · 2026-09-02 — Escape analysis
**Learn:** Escape analysis + scalar replacement + lock elision.

**Steps:**
1. [ ] Write a micro-benchmark where an allocation doesn't escape and gets scalar-replaced (near-zero allocation).
2. [ ] Compare with `-XX:-DoEscapeAnalysis` to show the difference.
3. [ ] Note lock elision on a non-shared lock.
4. [ ] 🤖 **Ask AI:** "Did EA actually kick in?"

**Done:** Micro-benchmark proves EA eliminated the allocation.

### Day 60 · Thu · 2026-09-03 — Deoptimization
**Learn:** Uncommon trap, speculative optimization, warm-up.

**Steps:**
1. [ ] Trigger a deopt: warm up a call site as monomorphic, then introduce a new type so the speculation fails.
2. [ ] Observe "made not entrant" / uncommon trap in `-XX:+PrintCompilation`.
3. [ ] 🤖 **Ask AI:** "Why did it deopt?"

**Done:** One deopt triggered and explained from the log.

### Day 61 · Fri · 2026-09-04 — JITWatch
**Learn:** Read compilation logs, inlining decisions, assembly.

**Steps:**
1. [ ] Run your benchmark with `-XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation`.
2. [ ] Open the log in JITWatch; find a hotspot that wasn't optimized well.
3. [ ] 🤖 **Ask AI:** "Which hotspot is under-optimized?"

**Done:** JITWatch run on a benchmark; under-optimized hotspot found.

### Day 62 · Sat · 2026-09-05 — Reading assembly
**Learn:** `-XX:+PrintAssembly` (hsdis); spotting vectorization (SIMD).

**Steps:**
1. [ ] Install hsdis into the JDK; run `-XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly` on one hot loop.
2. [ ] Find SIMD/vectorized instructions (e.g. `vmovdqu`, `vaddps`) in the disassembly.
3. [ ] 🤖 **Ask AI:** Explain the opcodes.

**Done:** Assembly of a hot loop read; vectorization recognized.

### Day 63 · Sun · 2026-09-06 — Review W9
**Steps:**
1. [ ] Write a one-pager: "`.java` → CPU instructions" (source → bytecode → JIT → machine code).
2. [ ] 🤖 **Ask AI:** Verify.

**Done:** One-pager .java→CPU instructions; verified against docs.

---

## Week 10 — Profiling & benchmarking: JMH, JFR, async-profiler, CLI tools

### Day 64 · Mon · 2026-09-07 — JMH fundamentals
**Learn:** Why `nanoTime` lies; warmup, Blackhole, `@State`, Mode.

**Steps:**
1. [ ] Create a JMH module (`mvn archetype` for jmh-java-benchmark).
2. [ ] Write a **correct** benchmark: `@State`, warmup iterations, `Blackhole`, `Mode.AverageTime`.
3. [ ] Write a naive `System.nanoTime()` version and compare the (misleading) numbers.
4. [ ] 🤖 **Ask AI:** "What's wrong with the naive version?"

**Done:** One correct JMH benchmark vs a naive one.

### Day 65 · Tue · 2026-09-08 — JMH pitfalls
**Learn:** Dead-code elimination, constant folding, loop optimizations.

**Steps:**
1. [ ] Write a "wrong" benchmark whose result is dead-code-eliminated (impossibly fast numbers).
2. [ ] Fix it by returning the value or `Blackhole.consume(...)`.
3. [ ] Show constant folding by making an input `final` vs `@State`.
4. [ ] 🤖 **Ask AI:** Review.

**Done:** Wrong benchmark fixed with Blackhole.

### Day 66 · Wed · 2026-09-09 — JFR
**Learn:** Events, low overhead, analysis in JMC.

**Steps:**
1. [ ] Record JFR: `-XX:StartFlightRecording=duration=60s,filename=rec.jfr` (or `jcmd <pid> JFR.start`).
2. [ ] Open `rec.jfr` in JDK Mission Control; find a hotspot in Method Profiling.
3. [ ] 🤖 **Ask AI:** "How do I read this event?"

**Done:** JFR recorded; hotspot found in JMC.

### Day 67 · Thu · 2026-09-10 — async-profiler
**Learn:** wall/cpu/alloc, flame graphs, differential.

**Steps:**
1. [ ] Profile a real app with async-profiler: `-e cpu` and `-e alloc`, output an interactive flame graph (`-o html`).
2. [ ] Read the flame graph; identify the widest (costliest) frame.
3. [ ] 🤖 **Ask AI:** "Which branch is expensive?"

**Done:** Flame graph of a real app produced + read; costly branch identified.

### Day 68 · Fri · 2026-09-11 — CLI diagnostics
**Learn:** jps/jstat/jstack/jmap; reading thread dumps.

**Steps:**
1. [ ] `jps -l` to find the PID; `jstat -gc <pid> 1s` to watch GC live.
2. [ ] `jstack <pid>` to capture a thread dump; find a BLOCKED/waiting thread (or deadlock).
3. [ ] 🤖 **Ask AI:** "Read this thread dump for me."

**Done:** Blocked thread found via jstack; dump read.

### Day 69 · Sat · 2026-09-12 — End-to-end tuning
**Learn:** App slow → profile → hypothesis → fix → re-measure.

**Steps:**
1. [ ] Pick a real app; measure a baseline metric (throughput/latency).
2. [ ] Profile → form a hypothesis → apply one fix → re-measure.
3. [ ] Record before/after numbers.
4. [ ] 🤖 **Ask AI:** Interpret the results.

**Done:** Full profile→fix→measure loop with a measurable improvement.

### Day 70 · Sun · 2026-09-13 — Review W10
**Steps:**
1. [ ] Write a "JVM diagnosis checklist for a slow app".
2. [ ] 🤖 **Ask AI:** "Which step is missing?"

**Done:** Complete JVM diagnosis checklist.

---

## Week 11 — Advanced & synthesis: MethodHandles, reflection, FFM, off-heap, capstone

### Day 71 · Mon · 2026-09-14 — MethodHandles & VarHandles as an API
**Learn:** vs reflection, performance.

**Steps:**
1. [ ] Build a fast field getter with `MethodHandles.lookup().findGetter(...)`.
2. [ ] Benchmark it against plain reflection (`Field.get`).
3. [ ] Explain why MethodHandles can be faster (JIT-friendly, no per-call access checks).
4. [ ] 🤖 **Ask AI:** "Why faster than reflection?"

**Done:** MethodHandle accessor beats reflection; you can explain why.

### Day 72 · Tue · 2026-09-15 — Reflection & dynamic proxy
**Learn:** Deep reflection + `java.lang.reflect.Proxy`.

**Steps:**
1. [ ] Wrap an interface in a logging proxy via `Proxy.newProxyInstance` + an `InvocationHandler`.
2. [ ] Measure the per-call overhead of going through the proxy.
3. [ ] 🤖 **Ask AI:** Review.

**Done:** Logging proxy around an interface + overhead measured.

### Day 73 · Wed · 2026-09-16 — Foreign Function & Memory API (Panama)
**Learn:** MemorySegment, Arena, C downcalls.

**Steps:**
1. [ ] Look up a libc function (e.g. `strlen` or `getpid`) via `Linker.nativeLinker()` + a downcall `MethodHandle`.
2. [ ] Allocate/pass native memory through an `Arena`; free it correctly by closing the arena.
3. [ ] 🤖 **Ask AI:** "Is my Arena/lifetime management correct?"

**Done:** libc function called via FFM; Arena manages lifetime correctly.

### Day 74 · Thu · 2026-09-17 — Off-heap & Unsafe
**Learn:** Direct ByteBuffer; safe successors to Unsafe (MemorySegment).

**Steps:**
1. [ ] Build an off-heap ring buffer with a direct `ByteBuffer` (or `MemorySegment`).
2. [ ] Compare with an on-heap version; list the risks (leaks, no bounds safety, manual lifetime).
3. [ ] 🤖 **Ask AI:** "What are the risks?"

**Done:** Off-heap ring buffer runs; risks stated.

### Day 75 · Fri · 2026-09-18 — Effective Java capstone (audit)
**Learn:** Audit code (equals/hashCode, builder, immutability, concurrency items 78–84).

**Steps:**
1. [ ] Audit all Week 1–5 code against an Effective Java checklist.
2. [ ] For each violation, note the item number and the fix.
3. [ ] 🤖 **Ask AI:** "Where do I break Effective Java?"

**Done:** W1–5 audited; EJ violations identified.

### Day 76 · Sat · 2026-09-19 — "Magic" mini-project: tiny APM
**Learn:** Combine bytecode timing (W8) + JFR (W10) + flame output.

**Steps:**
1. [ ] Build a small APM agent: instrument method timing (ByteBuddy) + emit JFR events + generate flame output.
2. [ ] Run it against a sample app and view the results.
3. [ ] 🤖 **Ask AI:** Full review.

**Done:** Tiny APM: bytecode timing + JFR + flame output.

### Day 77 · Sun · 2026-09-20 — Review W11 + cheat sheet
**Steps:**
1. [ ] Write a JVM/"wizard" cheat sheet **from memory** (no references).
2. [ ] 🤖 **Ask AI:** "Which important point is missing?"

**Done:** Wizard cheat sheet written from memory.

---

## Week 12 — Light review before the exam (reinforcement, no new topics)

### Day 78 · Mon · 2026-09-21 — Read one deep JEP (optional)
1. [ ] Read one deep JEP (Loom / Panama / ZGC) and summarize it in one paragraph. **No coding.**

**Done:** JEP summarized in a paragraph — no code.

### Day 79 · Tue · 2026-09-22 — Recall
1. [ ] Re-read your wizard cheat sheet; recall JVM concepts. **No new topics.**

**Done:** Concepts recalled — no new topics.

### Day 80 · Wed · 2026-09-23 — Reinforcement only
1. [ ] Reinforce only; load no new material. *(Java hands-on column is intentionally empty — rest the brain.)*

### Day 81 · Thu · 2026-09-24 — Reinforcement
1. [ ] Light review; optional full mock if you feel like it — go by energy, avoid burnout. *(No dedicated hands-on.)*

### Day 82 · Fri · 2026-09-25 — Night before the exam
1. [ ] Light skim + sleep early. *(No hands-on.)*

### Day 83 · Sat · 2026-09-26 — Exam morning
1. [ ] Warm-up quiz of 20–30 easy questions. *(No hands-on.)*

### Day 84 · Sun · 2026-09-27 — 🎯 AWS SAA-C03 EXAM DAY
**After the exam — pick a next direction (orientation, not today's coding):**
- [ ] Deep dive into Loom / virtual threads?
- [ ] GraalVM / native image?
- [ ] Contribute to OpenJDK?
- [ ] Professional performance engineering?

---

## How to use this document

1. Each day, open the matching section and work the checkboxes in **Steps** in order.
2. For every 🤖 **Ask AI** prompt, use it verbatim to self-test your understanding.
3. Only tick **Done** when you meet its criteria — that's the original "definition of done" from the plan.
4. Commit each day's code under its own package so the weekly rewrite/audit days can reference it.
