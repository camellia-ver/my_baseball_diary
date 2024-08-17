document.addEventListener('DOMContentLoaded', function() {
    var dateInput = document.getElementById('date');
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');
    var localDate = yyyy + '-' + mm + '-' + dd;
    dateInput.value = localDate;

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
