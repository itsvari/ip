# iP.AI Report

## TL;DR
I've used a _lot_ of models and tools, and I think LLMs are best suited for rubber duck debugging and writing boilerplate, and not as full-on replacements for writing code yourself. In my opinion, the best models as of mid-Feb 2025 are `Gemini 2.0 Flash Thinking`, `Gemini 2.0 Pro`, and `Claude 3.5 Sonnet`. For tools and providers, I like [Zed](https://zed.dev/) for its features (especially prompt libraries), and [GitHub Copilot](https://github.com/features/copilot) for its generous rate limits. IntelliJ's AI features are half-baked, and using the web interfaces adds too much friction. Running LLMs locally isn't quite meeting my own (admittedly high) standards just yet, but YMMV.

## Introduction

For the iP, I had initially thought of going down the [iP.AI](https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html#ip-feels-like-same-same) route, since I already had some experience working on a relatively large codebase (~9kLoC) project with friends, as a side project. In addition, I had been tracking the progress of LLM-assisted coding tools ever since the public preview of GitHub Copilot, but had found it not helpful in that earliest iteration - I had subsequently turned off Copilot and not used it since, so I thought it would have been interesting to jump back into using it again. However, after a couple of days of trying this route out, I felt that it had removed some of the fun of programming, and scaled back my use of large language models to primarily be for code review and help with boilerplate + documentation writing. Here are some of my detailed thoughts on my experience:

## Models Used

I used a mix of various models have been used throughout the development of this iP project. This has included, in no particular order:

* [`Claude 3.5 Sonnet`](https://docs.anthropic.com/en/docs/about-claude/models#model-comparison-table)
* [`GPT-4o`](https://platform.openai.com/docs/models#gpt-4o), [`o1-mini`](https://platform.openai.com/docs/models#o1), [`o1`](https://platform.openai.com/docs/models#o1), [`o3-mini`](https://platform.openai.com/docs/models#o3-mini)
* [`Gemini 1.5 Pro`](https://ai.google.dev/gemini-api/docs/models/gemini#gemini-1.5-pro), [`Gemini 2.0 Flash`](https://ai.google.dev/gemini-api/docs/models/gemini#gemini-2.0-flash), [`Gemini 2.0 Pro`](https://ai.google.dev/gemini-api/docs/models/experimental-models#available-models), [`Gemini 2.0 Flash Thinking Experimental`](https://ai.google.dev/gemini-api/docs/thinking)
* [`DeepSeek-R1`, `DeepSeek-R1-Distill-Llama-70B`, `DeepSeek-R1-Distill-Qwen-32B`, `DeepSeek-R1-Distill-Qwen-14B`](https://huggingface.co/deepseek-ai/DeepSeek-R1)
* [`Zed Industries Zeta`](https://huggingface.co/zed-industries/zeta)
* [`Qwen2.5-Coder-14B`](https://github.com/QwenLM/Qwen2.5-Coder)
* [`Meta LLaMa 3.2/3.2-vision/3.3` (multiple different quantizations)](https://www.llama.com/)
* [`Gemma 2.0 27B`, `Gemma 2.0 9B`](https://ai.google.dev/gemma)

### Observations

I found myself using `Claude 3.5 Sonnet`, the `Gemini 2.0` family, `o3-mini`, and `GPT-4o` the most. Although this was primarily based on the quality of their output, a significant factor was also their level of integration with my tools (elaborated further in the Tools Used section). I would primarily use the reasoning models for rubber duck debugging and code review, while the "traditional" and more-lightweight models (`Claude 3.5 Sonnet`, `GPT-4o`, `2.0 Flash`, `Zeta`) were used for code autocompletion and the first pass of documentation/Javadocs. Part of this reason is down to prompt processing speed - I code best when in a flow state, and the extended duration that reasoning models need to process prompts is long enough to break that state for me.

Chain-of-Thought (CoT) reasoning models were relatively helpful in thinking deeply about certain segments of code, but were limited by their smaller context windows (20k tokens/ktok for `o3-mini`, `o1`, and `o1-mini`, and 32ktok for `DeepSeek-R1` and its distillations), compared to `Claude 3.5 Sonnet` (200ktok) and especially Gemini and its variants (1Mtok/1500ktok for `Gemini 2.0 Flash`, 2Mtok/2000ktok for `Gemini 1.5 Pro` and `Gemini 2.0 Pro`). **I found that being able to fit large portions of the codebase (if not the entire codebase) into the model's context window led to producing output that was _significantly_ more usable, closer to what I had in mind, and which fit the code style guidelines.**

Surprisingly, despite not being a fully-fledged reasoning model, I felt that `Claude 3.5 Sonnet` wrote code which outperformed most reasoning models, and felt closest to the code structure that I had in mind. I suspect that this is partly due to Claude's [early lead in information retrieval across its entire context length](https://www.anthropic.com/news/claude-2-1-prompting), something which might have been retained ever since `Claude 2.1`. This capability is only matched by Gemini, [which has had a 1-2Mtok context window](https://storage.googleapis.com/deepmind-media/gemini/gemini_v1_5_report.pdf) since `Gemini 1.5 Flash`/`Gemini 1.5 Pro`, combined with excellent retrieval characteristics. However, at the time, the quality of `Gemini 1.5 Pro`'s output was worse than `Claude 3.5 Sonnet`, leading me to choose Claude most of the time.

Ever since [the general availability](https://blog.google/technology/google-deepmind/gemini-model-updates-february-2025/) of `Gemini 2.0 Flash Thinking`, it has now become my main model of choice, since it combines a huge context window (1MtoK) with CoT reasoning capabilities, high-quality output, reasonably quick speed, and good integration with my tools.

Lower-parameter count models and quantized models still have a measurable performance impact, and although their performance has certainly improved significantly, I have found that most of them don't quite clear my own bar of quality. Zed Industries' `Zeta` has been a notable exception, [though that is probably more down to its specialized nature, meant more for refactoring](https://zed.dev/blog/edit-prediction).

### Subjective Quality Ranking

#### Excellent

1. Reasoning Models (part 1)
   1. `o3-mini/Gemini 2.0 Flash Thinking`
1. `Claude 3.5 Sonnet`
1. Reasoning Models (part 2)
   1. `DeepSeek-R1`
   1. `o1`
   1. `o1-mini`
1. `Gemini 2.0 Pro`
1. `Gemini 2.0 Flash`, `GPT-4o`

#### Good

1. Reasoning Models (part 3)
   1. `DeepSeek-R1-Distill-Llama-70B`
   1. `DeepSeek-R1-Distill-Qwen-32B`
1. `Zed Industries Zeta`
1. `Gemini 1.5 Pro`
1. `LLaMa 3.3-70b`

#### Mediocre
1. Reasoning Models (part 4)
   1. `DeepSeek-R1-Distill-Qwen-14B`
1. `Qwen2.5-Coder-14B`
1. `Gemma 2.0 27B`

#### Bad
1. `LLaMa 3.2-Vision 11B`
1. `Gemma 2.0 9B`
1. `LLaMa 3.2 3B`
1. `LLaMa 3.2 1B`

## Providers Used

To date, I have only used the free tiers of various LLM inference providers. This has included:

* [GitHub Copilot Pro (via GitHub Student Developer Pack)](https://github.com/features/copilot)
  * Claude 3.5 Sonnet
  * GPT-4o, o1-mini, o1, o3-mini
  * Gemini 2.0 Flash
* [Zed AI](https://zed.dev/ai)
  * Claude 3.5 Sonnet
  * Zed Industries Zeta
* [Google AI Studio](https://aistudio.google.com/)
  * Gemini 1.5 Pro, 2.0 Flash, 2.0 Pro, 2.0 Flash Thinking Experimental
* [GroqCloud](https://groq.com/groqcloud/)
  * DeepSeek-R1-Distill-Llama-70B
  * Various LLaMa 3.2/3.3 quants
* [Cerebras Inference](https://cerebras.ai/inference)
  * DeepSeek-R1-Distill-Llama-70B
  * Various LLaMa 3.2/3.3 quants
* [SambaNova Cloud](https://cloud.sambanova.ai/)
  * DeepSeek-R1
  * DeepSeek-R1-Distill-Llama-70B
  * Various LLaMa 3.2/3.3 quants
* Web chat interfaces for [ChatGPT](https://chatgpt.com/), [Claude](https://claude.ai/), [DeepSeek](https://chat.deepseek.com/)
* Local inference via [Ollama](https://ollama.com/), [exo](https://github.com/exo-explore/exo), and [LMStudio](https://lmstudio.ai/)
  * Models:
    * DeepSeek-R1-Distill-Qwen-32B
    * DeepSeek-R1-Distill-Qwen-14B
    * Qwen2.5-Coder-14B
    * LLaMa 3.2 1B, LLaMa 3.2 3B, LlaMa 3.2-vision 11B
    * Gemma 2.0 27B, Gemma 2.0 9B
  * Local machine specs:
    * `vexcalibur`: 2023 16" M2 Pro MacBook Pro
      * CPU: Apple M2 Pro (8p4e, 12t @ 3.7/3.4 GHz)
      * GPU: Apple M2 Pro (19c, 27.2 TFLOPS FP16, ~54.4 TOPS INT8)
      * NPU: Apple M2 Pro (16c, 15.8 TOPS INT8)
      * RAM: 16GB DDR5-6400 unified memory
      * OS: macOS Sequoia 15.x
    * `witherhoard`: custom-built desktop gaming PC
      * CPU: Ryzen 7 7800X3D (8p, 16t @ 5 GHz)
      * GPU: Sapphire Pulse AMD Radeon RX 7800 XT, 16GB GDDR6 VRAM (74.69 TFLOPS FP16, ~149 TOPS INT8)
      * RAM: 32GB DDR5-6000 CL36 (2x 16GB)
      * OS: Arch Linux + Windows 11 dual-boot (Linux used for AI inference)
    * `memory-alpha`: custom-built homelab/server/NAS
      * CPU: Intel i5-6600 (4p, 4t @ 3.3 GHz)
      * GPU: AMD Radeon RX 6750 XT, 12GB GDDR6 VRAM (26.62 TFLOPS FP16, ~53 TOPS INT8)
      * RAM: 32GB DDR4-2133 CL16 (4x 8GB)
      * OS: TrueNAS Scale

### Speed Ranking (experience-based, not strictly benchmarked)

1. Cerebras
1. SambaNova/Groq
1. Google AI Studio
1. Zed AI
1. Anthropic (Claude) + OpenAI (ChatGPT) web UI
1. GitHub Copilot
1. Local inference (via Ollama or LMStudio)
    1. Cluster with exo/solo running on `witherhoard`
    1. `vexcalibur`
    1. `memory-alpha`
1. DeepSeek web UI

Out of all of these providers, I found myself using GitHub Copilot and Zed AI the most, due to the combination of ease of integration with my workflow (elaborated more in the Tools Used section), speed of inference, and having generous limits. Google's AI Studio was a close second, due to the exclusive access to `Gemini Flash 2.0 Thinking Experimental`, as well as their relatively fast inference speeds. I believe this is due to their [significant, long term investment in their Tensor Processing Unit (TPU) hardware](https://cloud.google.com/tpu). Since it's a dedicated AI ASIC of their own custom design, with [nearly a decade's worth of iteration behind it](https://cloud.google.com/blog/products/compute/trillium-tpu-is-ga), it's highly likely that their running costs are relatively lower compared to the competition, and can offer free users much better compute quality.

Groq, Cerebras, and SambaNova are all trying to create dedicated AI inference silicon, and have seemingly succeeded - they all offer inference rates in the hundreds, if not thousands, of tokens per second for normal models. [Groq strings together hundreds of their LPUs for a single instance](https://groq.com/wp-content/uploads/2024/07/GroqThoughts_WhatIsALPU-vF.pdf), [Cerebras is doing _**full-wafer scale**_ chips](https://cerebras.ai/product-chip/), and [SambaNova has an interesting three-tiered memory architecture on their RDUs](https://sambanova.ai/technology/sn40l-rdu-ai-chip). Notably though, Cerebras manages to maintain a ~1000-2000 token/sec inference rate even when running `Deepseek-R1-Distill-Llama-70B`, while Groq and SambaNova slow down to ~200 tokens/sec - glacial in comparison! Regardless, they have managed to run frontier models at _incredible_ speeds that are much faster than traditional GPUs, and have APIs that are compatible with most apps. However, they all have more restrictive free plans, and have a much narrower selection of available models, which unfortunately restricts their usefulness for my use case.

Zed AI was marginally faster than both Anthropic and OpenAI's web interfaces in my experience - this is likely due to Zed AI being a lightweight, text-only experience, while Anthropic and OpenAI have heavier frontends. GitHub Copilot was slightly slower than both, even when working via Zed's text-only interface, even though both GH Copilot and OpenAI run on Microsoft Azure servers. I'm guessing that this is probably because Microsoft/GitHub are doing additional filtering to ensure that GitHub Copilot only answers coding-related questions, alongside some traffic deprioritization.

I had by far the worst experience trying to use DeepSeek's own web UI for inference - it was clear that they are still being flooded by requests from many people, and do not have the infrastructure capable of handling their demand. I would often encounter server timeouts, and when my requests weren't being timed out, they were _agonisingly_ slow. Thankfully, since `Deepseek-R1` is an open-source model, there have been other providers that offer the same model at much faster inference rates.


### Local LLM Inference

Despite the advances promoted by DeepSeek, as well as the various startups developing dedicated AI inference ASICs, LLM inference is still _really_ expensive - [OpenAI had an estimated $8.7 billion in expenses in 2024](https://www.nytimes.com/2024/09/27/technology/openai-chatgpt-investors-funding.html). The entire industry is in a bubble [propped up by tons of VC cash and institutional FOMO](https://techcrunch.com/2025/02/11/ai-investments-surged-62-to-110-billion-in-2024-while-startup-funding-overall-declined-12-says-dealroom/). Leading AI chip startups have lots of petrostate/sovereign wealth fund investment ([Groq has Saudi Arabia's PIF](https://groq.com/saudi-arabia-announces-1-5-billion-expansion-to-fuel-ai-powered-economy-with-groq/), [Cerebras has Abu Dhabi's Growth Fund and G42](https://cerebras.ai/press-release/cerebras-systems-raises-250m-in-funding-for-over-4b-valuation-to-advance-the-future-of-artificial-intelligence-compute/), and [SambaNova has Singapore's Temasek + GIC _and_ Saudi Arabia/SoftBank's Vision Fund](https://www.straitstimes.com/business/companies-markets/ai-startup-sambanova-raises-us676m-from-investors-including-temasek-gic)), but even those investors have their limits. The only reason that everyone has had so much free access to inference on these frontier models is because of the current AI bubble, which _will_ eventually and inevitably pop; it's just a matter of when. In addition, I wanted to test the use case of AI-assisted coding in an offline scenario. Plus, it'll be much nicer if we don't have to spend ridiculous amounts of power and money on AI compute.

Thus, to see if running LLMs locally for coding at a similar level of quality was viable, I experimented with using local-only inference, instead of relying on external cloud providers. However, I ran into several problems:
1. Every machine that I own has a maximum VRAM capacity of 16GB, which effectively limits me to models of ~14B parameters or less, if I want to fit it entirely within GPU VRAM on a single machine. Going out to system memory incurs a significant speed penalty, which I find unacceptable. Although work on LLMs of this scale has been advancing rapidly, I personally do not feel that the results are of adequate quality yet.
   1. [Donations for me to acquire an RTX 5090 or other better hardware are gladly accepted](https://ko-fi.com/itsvari/).
1. `vexcalibur` is the main machine that I do my development work on, and it runs inference at slightly less than half the speed of `witherhoard`. `Deepseek-R1-Distill-Qwen-14B` and `Qwen2.5-Coder-14B` run a bit too slowly for my liking (~20 tokens/sec), heat up my laptop to uncomfortable levels if used on a lap, and absolutely _chew_ through the battery. `LLaMa-3.2-3B` and `LLaMa-3.2-1B` run at acceptable speeds, but produce mostly bad/unusable output most of the time. I _can_ remote into `witherhoard` by using [Tailscale](https://tailscale.com/), but that requires me to leave my desktop PC on at home all the time. `memory-alpha` runs 24-7, but performs _worse_ than `vexcalibur` anyways, making it pointless.
1. Due to Apple being Apple, it is extremely hard to specifically use the NPU on Apple Silicon Macs, and very few models are optimised to run on MLX (the Apple Silicon-optimised model framework) - most useful models are in the GGUF standard, which can only run on the Mac's GPU. Even if the useful models _could_ run on the NPU, it only has under 1/3 the power of the GPU, and likely wouldn't speed up inference by much anyway.
1. `memory-alpha` is frankly too underpowered to be of good use for LLM inference, and it's already under some load (being a Plex + backup server, as well as other apps). Plus, `ollama` has to run in a Docker instance to be able to run properly on TrueNAS Scale, meaning even _more_ overhead.
1. Locally-run models seemed to get trapped in loops much more easily, and were prone to producing completely nonsensical/garbage output if the reply token count got too long.
1. I tried using `exo` to run larger models by splitting the workload across all three devices. The setup was functional, but incurred significant levels of overhead, and ended up producing output at ~20 tokens/sec, which I find to be unacceptably slow, as mentioned earlier.

Thus, due to all the aforementioned issues, I gave up relatively quickly on trying to use local LLM inference for this project. Local LLM inference might be decent enough for more casual use-cases, but I personally have more exacting standards when it comes to writing code. For me to consider local inference as a viable alternative for coding, one of these two things would need to happen (ideally both):
1. I need have access to much more powerful hardware (bonus points for portability).
2. Smaller models would need to improve in quality to the same level as _current frontier models_.

## Tools Used

I've used a few different tools for utilising LLMs in my code, which are (in no particular order):

1. IntelliJ GitHub Copilot integration
1. Zed Editor integration
   1. Zed AI
   1. GitHub Copilot API
   1. Google AI Studio API
   1. Ollama local server
   1. LMStudio local server
   1. Groq, Cerebras, SambaNova API
1. Visual Studio Code GitHub Copilot integration
1. Ollama local CLI
1. LMStudio desktop app
1. Web chat interface
   1. Anthropic Claude
   2. OpenAI ChatGPT
   3. Google AI Studio
   4. DeepSeek

After a few weeks, I've settled on these couple few tools:
1. First pass of writing new code with AI assistance in Zed, all prompts go through Zed
1. Manual code refinement + refactoring in IntelliJ, AI use limited to simple tab-completion
1. If Google API limits are exhausted: use Google AI Studio web interface with other accounts

As stated multiple times in this post, my golden rule for LLM-assisted coding is that ***it must not break my flow***. I do _not_ want to spend lots of time switching back and forth between windows, copying and pasting items, and response time matters to me. Out of all of these methods, I strongly prefer using Zed's LLM integrations. Zed provides an extremely robust and poweruser-friendly set of tools for working with LLMs - I can freely view and edit the _entire_ prompt that is sent to the various LLMs, even down to editing the LLM's responses too. Zed has the ability to create a user-defined "library" of prompts that can easily be added to any query, and can auto-inject a default set of prompts to _every_ query sent to the LLM - even for tab-autocompletion and inline prompting! This has been extremely useful in ensuring that all generated code has stuck to the project's style guidelines from the beginning, and in ensuring clean code practices. The ability to quickly add files to a prompt (all I need to do is to type `/file FILENAME`), insert changes, and even swap models without losing context is extremely helpful for staying in my flow. Finally, because it's a fully text-based, lightweight interface, it feels more responsive than the web chat interfaces that other models use.

In comparison, the GitHub Copilot integration in Visual Studio Code and IntelliJ is severely half-baked, slower, and often requires more use of the (slightly buggy) GUI. VSCode's integration is slightly better and less buggy, and at least it allows me to select which model to use - IntelliJ's version doesn't even allow that! However, tab-autocompletion works reasonably well, if given sufficient context within the file itself for proper hints of the preferred coding style. As such, it's the only AI feature that I regularly use within IntelliJ.

I rarely use the web chat interfaces, since there's extra friction added when trying to add the relevant code files for prompt context, and there's no easy way to copy and paste code snippets from my IDE/editor over to the web browser window. The one exception is [Google AI Studio](https://aistudio.google.com/), which I only use on my other Google accounts when I've exhausted the free tier limits on my main account. Thankfully, the interface also allows me to set the system prompt, edit any of the user and assistant prompts, and also allows me to attach multimedia (since Gemini is a native multimodal model). This was useful in debugging the MVP GUI for the iP, but not much else.

For the short period of time in which I used local LLMs, I primarily used the Ollama integration within Zed, and rarely used the CLI interface. LMStudio seemed to run the same models at a marginally slower rate than Ollama, and I had little use for the fancier GUI of the desktop app, since it had the same issues that web chat apps had: adding source code file and transferring code snippets was a hassle.

## Conclusion

In conclusion, I've found that LLMs are best suited for rubber duck debugging and writing boilerplate, and not as full-on replacements for writing code yourself. I think that smaller LLMs need to get significantly better to be able to be run on most hardware at reasonable power levels, and a couple more generations of refinement are needed on the hardware side - the era of infinite cheap AI compute _will_ come to a close at some point, and we've just gotten to a point where LLMs can be decent productivity boosters for coding, rather than being an annoying feature to turn off immediately.