document.addEventListener('DOMContentLoaded', function() {
    var dateInput = document.getElementById('date');
    var today = new Date().toISOString().split('T')[0];
    dateInput.value = today;

    var simplemde = new SimpleMDE({
        element: document.getElementById("markdown-editor"),
        spellChecker: false,
        toolbar: [
          "bold", "italic", "heading", "|",
          "quote", "code", "unordered-list", "ordered-list", "|",
          "link", "image", "table", "|",
          "preview", "side-by-side", "fullscreen"
        ]
      });

    var dateInput = document.getElementById('date');

    dateInput.addEventListener('change', function(event) {
        var selectedDate = event.target.value;

    });
});
