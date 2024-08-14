document.addEventListener('DOMContentLoaded', function() {
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
});

function showPopupAndRedirect(redirectUrl) {
    alert("일지를 작성 가능한 경기데이터가 없습니다.");
    setTimeout(function() {
        window.location.href = redirectUrl;
    }, 2000);
}
