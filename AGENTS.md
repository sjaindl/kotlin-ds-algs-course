# AGENTS.md

## Course material (exercises, visualizers, Udemy exercises, toolbox, common bugs)

Use the `lecture-studio-generation` skill (steff-fleet `skills/lecture-studio-generation`) to create or change
course material. It holds the rules for each kind; read its file before writing:

| Material | Where | Reference |
|---|---|---|
| Exercise: starter, solutions, runner | `src/main/kotlin/questions/<topic>/<name>/` | `questions/array/rotate_array/`, `max_profit/` |
| Local tests | `src/test/kotlin/questions/<topic>/<name>/<Name>Test.kt` | `rotate_array/RotateArrayTest.kt` |
| Visualizer page | `<Name>.html` next to the starter, styles in `questions/visualizer.css` | `rotate_array/RotateArray.html`, `max_profit/MaxProfit.html` |
| Udemy learning objective and hints | `lectures/<topic>/exercises/<name>.md` | `lectures/array/exercises/rotate_array.md` |
| Udemy tests | `src/test/kotlin/questions/<topic>/<name>/Evaluate.kt` | `rotate_array/Evaluate.kt` |
| Toolbox | `src/main/kotlin/toolbox/<topic>/` | `toolbox/array/` |
| Common bugs | `src/main/kotlin/common_bugs/<topic>/CommonBugs.md` | `common_bugs/array/CommonBugs.md` |

Each coding question gets its own folder: topic + snake_case name, e.g. `questions/array/rotate_array/`.

## Lectures (slides, speaker script, voice-over, video)

Use the `lecture-studio-recording` skill (steff-fleet `skills/lecture-studio-recording`, CLI in
`services/lecture-studio`) for lecture slides, speaker scripts, voice-overs, slide images and the lecture video.
Settings (deck, voice profile, transition) are in `lecture.toml`. Per lecture, the original speaker script is
`lectures/<topic>/<nn>_<snake_case_title>.md` and the spoken version the CLI voices is `..._voicebox.md` next to it.
Tracks live in `lectures/<topic>/audio/`, slide images in `lectures/<topic>/slides/`, videos in
`lectures/<topic>/video/`, all named without the `_voicebox` suffix. `lectures/string/01_character_sets.md` /
`01_character_sets_voicebox.md` are the first example.
