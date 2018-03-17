var allQuestions = [{
    question: "Elements in the periodic table are arranged by - ",
    choices: ["Atomic Number", "Atomic Weight", "Number of Neutrons", "Chemical Reactivity"],
    correctAnswer: "Atomic Number"
}, {
    question: "Which of these things are NOT listed in the periodic table ?",
    choices: ["Element Name or Symbol", "Atomic Weight", "Orbital Radius", "Atomic Number"],
    correctAnswer: "Orbital Radius"
}, {
    question: "Which of these choices is NOT a family of elements ?",
    choices: ["Halogens", "Alkali Metals", "Noble Gases", "All are family of elements"],
    correctAnswer: "All are family of elements"
}, {
    question: "The atomic number of an element tells you the number of _____ in a neutral atom.",
    choices: ["Positrons", "Neutrons", "Electrons", "None of Above"],
    correctAnswer: "Electrons"
}
    ];


var Quiz = {

    count: 0,
    correctAnswerCount: 0,

    showQuestion: function() {
        $('.questions').html(''); //clear question
        $('.choices').html(''); //clear answers
        $('.correct-answers').html(''); //clear correct answers
        $('.next-button').text('Next Question').removeClass('btn-success');
        if (this.count < allQuestions.length) {
            var question = allQuestions[this.count].question;
            var id = 1;
            $('.questions').prepend('<p>' + question + '</p>');

            allQuestions[this.count].choices.forEach(function(choice) {
                if (id === 1)
                    $('.choices').append('<div class="radio"><label><input type="radio" name="optionsRadios" id="optionsRadios' + id + '" value="' + choice + '" checked>' + choice + '</label></div>');
                else
                    $('.choices').append('<div class="radio"><label><input type="radio" name="optionsRadios" id="optionsRadios' + id + '" value="' + choice + '">' + choice + '</label></div>');
                id++;
            });
        } else {
            $('.correct-answers').html(this.correctAnswerCount + ' / ' + allQuestions.length + ' answered correctly!');
            $('.next-button').text('Start Over').addClass('btn-success');
            this.count = 0;
            this.correctAnswerCount = 0;
        }
    },

    checkAnswer: function(answer) {
        if (answer && answer === allQuestions[this.count].correctAnswer)
            this.correctAnswerCount += 1;
        this.count += 1;
    }

};

$(function() {
    //show first question
    Quiz.showQuestion();

    //event listener
    $('.next-button ').on('click', function() {
        var answer = $('input[name="optionsRadios"]:checked').val();
        Quiz.checkAnswer(answer);
        Quiz.showQuestion();
    });
});
