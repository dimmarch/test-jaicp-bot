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
                
                var answer = "<b>Путь:</b> " + intent.debugInfo.intent.path +
                    "<br><b>Итоговый вес:</b> " + score 
                $response.replies.push({
                    "type": "text",
                    "text": answer,
                    "markup": "html"
                });
            }
            
            $reactions.answer("Выбранный интент: " + $context.intent.path)
