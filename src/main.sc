require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>

    state: NoMatch
        event!: noMatch

    state: Match
        event!: match
        script:
            $response.replies = $response.replies || [];
            var intents = $context.nluResults.intents
            
            $reactions.answer("Найденные интенты:")
            
            for (var index in intents) {
                
                var intent = intents[index]
                var score = Number(intent.score).toFixed(4)
                var patternsScore = Number(intent.debugInfo.weights.patterns).toFixed(4)
                var examplesScore = Number(intent.debugInfo.weights.phrases).toFixed(4)
                
                var answer = "<b>Путь:</b> " + intent.debugInfo.intent.path +
                    "<br><b>Итоговый вес:</b> " + score +
                    "<br><b>Вес паттернов:</b> " + patternsScore +
                    "<br><b>Вес примеров:</b> " + examplesScore
                $response.replies.push({
                    "type": "text",
                    "text": answer,
                    "markup": "html"
                });
            }
            
            $reactions.answer("Выбранный интент: " + $context.intent.path)
