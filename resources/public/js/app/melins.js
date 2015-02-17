(function ($, signs) {
    var coords = signs.coords;

    $(document).ready(function () {
        $("#text-input").keyup(function () {
            var canvas = $("#canvas")[0];
            var text = $(this).val();
            var words = textToWords(text);
            var wordSet = words.map(signSet);


            var dimensions = {
                margin: {x: 60, y: 120},
                border: {x: canvas.width, y: canvas.y}
            };

            var placedWords = placeWords(wordSet, dimensions);

            draw(canvas, placedWords);
        });
    });

    var draw = function (canvas, words) {
        var context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.height * 3, canvas.width * 3);

        words.forEach(function (word) {
            word.signs.forEach(function (signSet) {
                drawSign(context, signSet.sign, signSet.x + word.x, signSet.y + word.y);
            });
        });
    };

    var drawSign = function (context, sign, x, y) {
        var img = new Image();
        img.src = "img/" + sign + ".png";
        img.onload = function () {
            context.drawImage(this, x, y);
        };
    };

    var textToWords = function (text) {
        return text.trim().split(" ").map(function (word) {
            return word.split("").filter(function (sign) {
                return sign in coords;
            });
        });
    };

    var signSet = function (word) {
        var x = 0,
            y = 0;

        var signs = word.map(function (sign) {
            x += coords[sign].end.x - coords[sign].start.x;
            y += coords[sign].end.y - coords[sign].start.y;
            return {
                sign: sign,
                x: x - coords[sign].end.x,
                y: y - coords[sign].end.y
            };
        });

        return {
            x: 0,
            y: 0,
            signs: signs,
            width: signs.length === 0 ? 0 : Math.abs(signs[signs.length - 1].x - signs[0].x)
        };
    };

    var placeWords = function (words, dim) {
        var marginX = 60;
        var marginY = 120;
        var x = marginX;
        var y = marginY;
        var placedWords = [];

        words.forEach(function (word) {
            if (word.width + x > dim.border.x - dim.margin.x && x != dim.margin.x) {
                y += 60;
                x = marginX;
            }
            placedWords.push({
                x: x,
                y: y,
                signs: word.signs
            });
            x += word.width + 30;
        });

        return placedWords;
    };

})(jQuery, signs);
