# AGENTS.md

## Exercise visualizer pages

Exercises (e.g. `src/main/kotlin/questions/array/max_profit/`) can have a standalone HTML page that illustrates
the assignment graphically and lets you step through a solution. Reference implementation:
`src/main/kotlin/questions/array/max_profit/MaxProfit.html`.

### Location and naming
- Place the page next to the task file, named like it: `MaxProfit.kt` → `MaxProfit.html`.
- Single self-contained file: inline CSS and JS, no external dependencies, must work when opened from disk.

### Head
- `<title>`: short exercise name only (e.g. `Max Profit`), no long sentences.
- Favicon: the Udemy logo in the repo root, linked relatively from the page's folder, e.g.
  `<link rel="icon" type="image/png" href="../../../../../../udemy.png">` (adjust `../` to the folder depth).

### Page structure (top to bottom)
1. `<h1>` with a descriptive heading (e.g. "Calculate max profits to buy and sell a stock").
2. A card with the problem description copied verbatim from the task's comment block (without examples/constraints).
3. One line naming the strategy that is visualized, referencing the matching file in `solution/`.
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
- Colors as CSS variables on `:root`, with a `prefers-color-scheme: dark` override; responsive layout.
- Keep the JS small, readable and commented, in the style of the course code.
- Verify the step logic (e.g. with `node`) against the task's examples and edge cases (single element, plateaus).
