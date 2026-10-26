# AGENTS.md

## Exercise structure

Each coding question gets its own folder, e.g. `questions/array/rotate_array/` (topic + snake_case name).
Reference: `src/main/kotlin/questions/array/rotate_array/` and `max_profit/`.

```
src/main/kotlin/questions/<topic>/<name>/
    <Name>.kt                    starter for students
    <Name>.html                  visualizer page (see below)
    solution/<Name><Approach>.kt one file per approach, e.g. RotateArrayReverse.kt, MaxProfitGreedy.kt
    solution/<Name>Runner.kt     runs every approach on the task's examples
src/test/kotlin/questions/<topic>/<name>/<Name>Test.kt
```

### Starter `<Name>.kt`
- Package `questions.<topic>.<name>`, then one comment block:
  - `<number>. <Title> (<Difficulty>)` and the LeetCode URL
  - the description, examples and explanations copied verbatim
  - constraints with exponents written as `10^5` / `-2^31`
  - follow-up, if the task has one
  - `Your turn! Implement <fun> below, then check it with <Name>Test or <Name>Runner.`
  - `n: <what n is>` / `T: ?` / `S: ?` for the student to fill in
- A top-level function with the task's signature (no `class Solution`, no `: Unit`), whose body is
  `TODO("Your solution here")`.

### Solutions `solution/<Name><Approach>.kt`
- Package `questions.<topic>.<name>.solution`. Each approach is a top-level `fun <name><Approach>(...)` with a
  private helper if it needs one.
- Header comment: the idea in 1–3 sentences, an optional worked example, then `n: …` / `T: O(…)` / `S: O(…)` and
  language-neutral pseudocode.
- Typical approaches, worst to best: `Bruteforce`, then intermediate ones (`ExtraArray`, `PeakValley`, …), then
  the optimal one. Reduce inputs where that's free (e.g. `k % n`).
- Always break after `{`: no single-line brace blocks (`if (x) return` without braces is fine).

### Runner `solution/<Name>Runner.kt`
- `examples` holds the task's examples with expected results. `approaches` is a list of name to function
  reference for the `solution/` approaches only, not the starter.
- Prints input, expected and every approach's result, and shows `not implemented yet` on `NotImplementedError`.
- Functions that mutate their input get a `copyOf()` per approach.

### Local tests `src/test/kotlin/.../<Name>Test.kt`
- `kotlin.test`. `cases` is a list of input to expected, one short comment per case. Cover:
  - the task's examples
  - edge cases: single element, zero / boundary parameters, all equal values
  - cases that break specific approaches (e.g. gcd(n, k) > 1 for cyclic replacements)
  - constraint bounds (`Int.MIN_VALUE` / `Int.MAX_VALUE`)
- A `verify(solution)` helper (copy mutable inputs first), then one `@Test` per approach plus `` `starter solution` ``.
  The starter test is expected to fail with `NotImplementedError` until the student implements it.

### Learning objective
For the Udemy exercise text, write one short paragraph of what is learned (approaches compared, target
complexity), then 4–5 "After this exercise you can …" bullets and an optional one-sentence version.

## Toolbox

After a topic's lecture, collect its reusable techniques in `src/main/kotlin/toolbox/<topic>/` (package
`toolbox.<topic>`). Reference: `src/main/kotlin/toolbox/array/`.

- One file per concept, e.g. `CyclicIndex.kt`, `ReverseTrick.kt`. Avoid names that clash with Kotlin types
  (`DoubledArray.kt`, not `DoubleArray.kt`).
- Header comment: the idea in 1–3 sentences, when to reach for it, `Seen in:` with the exact `questions/...` files,
  Kotlin pitfalls if any, a short worked example, then `n: …` / `T: O(…)` / `S: O(…)`.
- Then 1–3 small, generic top-level functions or extensions with a one-line comment each, not a copy of a solution.
- `runners/<Topic>Toolbox.kt` prints a short demo per concept. `src/test/kotlin/toolbox/<topic>/<Topic>ToolboxTest.kt`
  has one `@Test` per snippet with edge cases.
- Leave out techniques that belong to a later topic (e.g. monotonic stack → stack toolbox) or only fit one problem.

## Common bugs

Per topic, list the bugs students typically hit in one Markdown file, `src/main/kotlin/common_bugs/<topic>/CommonBugs.md`.
Reference: `src/main/kotlin/common_bugs/array/CommonBugs.md`.

- `# Common bugs in <topic> questions`, a one-sentence intro, then one numbered `## <n>. <Title>` per bug (~10–15).
- Each bug: a 1–2 sentence description (what goes wrong, the symptom: exception, wrong result, endless loop), an
  optional `Seen in:` with the exact `questions/...` or `toolbox/...` files, then a **Wrong** and a **Correct**
  `kotlin` snippet of a few lines. Keep the snippets otherwise identical so the fix stands out, and comment the
  concrete input and output where it helps, e.g. `// [1, 2, 3] -> [3, 1, 1]`.
- Mix classic bugs (off-by-one, empty input, overflow, min/max start values) with Kotlin pitfalls (`%` vs `.mod()`,
  `==` on arrays, aliasing) and ones from the topic's questions (e.g. gcd cycles in rotate array).
- Verify every snippet with a scratch `kotlinc -script` file: the wrong one shows the described bug, the correct one
  doesn't, and the comments match the real output. Don't store the script in the repo.

## Udemy coding exercise tests

Udemy runs its own Kotlin evaluator. These tests are written for copy & paste into the Udemy editor and are
not stored in the repo. The rules differ from the local tests:

- **JUnit 5 only**: `org.junit.jupiter.api.Test`, `org.junit.jupiter.api.Assertions.*`. No `kotlin.test`, no
  JUnit 4 (`org.junit.Assert`, `@Test(timeout = …)` don't compile).
- The test class must be named **`Evaluate`** and must **not** have a `package` declaration.
- The student's code has no package either (unless the task asks for one, then import it), so call the starter
  function directly, e.g. `rotate(nums, k)`. If the starter is a class, instantiate it as a field.
- Assertions take the message **last**: `assertEquals(expected, actual, "msg")`,
  `assertArrayEquals(expected, actual, "msg")`. Use `fail<Nothing>("msg")` for custom failures.
- Write messages for students: name the input and what came back, e.g.
  `"rotate([1, 2, 3], k = 10) gave [1, 2, 3]"`.
- One `@Test` per case (named after the case, e.g. `kGreaterThanLength`) so students see which case failed.
- Performance: use `assertTimeoutPreemptively(Duration.ofSeconds(1), { … }, "… aim for O(n) time")` with
  inputs at the constraint limits. Repeat the call a few times so the slow approach clearly exceeds the limit,
  because the JIT makes a single naive run surprisingly fast.
- For stdout / stdin there are `com.udemy.ucp.IOHelper` (`captureOutput()`, `output`, `setStdin(...)`) and
  `com.udemy.ucp.EvaluationHelper` (`isMethodDeclared`, `isFieldDeclared`, `getFieldValue`, …).
- Verify locally before handing it out:
  1. Temporarily copy the test to `src/test/kotlin/Evaluate.kt` with an alias import of each solution, e.g.
     `import questions.array.rotate_array.solution.rotateReverse as rotate`.
  2. Run `./gradlew test --rerun-tasks --tests Evaluate` and check `build/test-results/test/TEST-Evaluate.xml`.
  3. Delete the file again.

  The optimal solutions must pass, and the timeout must fail the brute force where intended.

## Exercise visualizer pages

Exercises (e.g. `src/main/kotlin/questions/array/max_profit/`) can have a standalone HTML page that illustrates
the assignment graphically and lets you step through a solution. Reference implementations:
`src/main/kotlin/questions/array/max_profit/MaxProfit.html`,
`src/main/kotlin/questions/array/rotate_array/RotateArray.html`.

### Location and naming
- Place the page next to the task file, named like it: `MaxProfit.kt` → `MaxProfit.html`.
- Inline JS, no external (network) dependencies, must work when opened from disk.
- Shared styles live in `src/main/kotlin/questions/visualizer.css`; link it relatively, e.g.
  `<link rel="stylesheet" href="../../visualizer.css">`. Keep only page-specific color tokens and rules in an inline
  `<style>`. Add new generic rules to the shared file instead of copying them.

### Head
- `<title>`: short exercise name only (e.g. `Max Profit`), no long sentences.
- Favicon: the Udemy logo in the repo root, linked relatively from the page's folder, e.g.
  `<link rel="icon" type="image/png" href="../../../../../../udemy.png">` (adjust `../` to the folder depth).

### Page structure (top to bottom)
1. `<h1>` with a descriptive heading (e.g. "Calculate max profits to buy and sell a stock").
2. A card with the problem description copied verbatim from the task's comment block (without examples/constraints).
3. One line naming the strategy that is visualized, referencing the matching file in `solution/`. To show several
   approaches, put tabs above it (`.tabs` / `.tab` from `visualizer.css`). Use one step builder per approach, all
   producing the same snapshot shape, and keep the selected tab in the URL hash (see `RotateArray.html`).
4. Input card: buttons for each example from the task, a "Random" button, and a text field + "Load" for custom
   input with validation and an inline error message.
5. Visualization card: inline SVG (scaled via `viewBox`) of the input, highlighting the structure that matters for
   the problem. Show a cursor for the current step and fade parts not reached yet.
6. Controls: ⏮ Reset, ◀ Back, Next ▶, ▶ Play/Pause (~800 ms per step); keyboard `←`/`→` step, `Space` play/pause.
7. State panel: current step / position, relevant state values, a prominent **result counter**, a sentence
   explaining the decision made in this step, and a log of completed actions.
8. At the end: the final result with a ✓/✗ cross-check against another solution approach from `solution/`.

### Implementation
- Port the algorithm from the corresponding `solution/*.kt` file and precompute an array of step snapshots;
  Back/Next/Play just move an index and re-render from the snapshot.
- Colors as CSS variables on `:root`, with a `prefers-color-scheme: dark` override; responsive layout. Base tokens
  (`--bg --panel --text --muted --border --accent --cursor --good --bad`) come from `visualizer.css`; set `--counter`
  to color the result counter (`.stat .value.counter`).
- Keep the JS small, readable and commented, in the style of the course code.
- Verify the step logic (e.g. with `node`) against the task's examples and edge cases (single element, plateaus).
