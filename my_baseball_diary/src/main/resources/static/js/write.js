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
    const result = confirm("오늘의 경기가 존재하지 않습니다. 이전 경기의 정보를 가져 오시겠습니까?");

    if (!result) {
        setTimeout(() => {
            window.location.href = redirectUrl;
        }, 2000);
    }
}
