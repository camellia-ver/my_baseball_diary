document.addEventListener('DOMContentLoaded', function() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();

    var todayFormatted = yyyy + '-' + mm + '-' + dd;
    document.getElementById('date').value = todayFormatted;

    var simplemde = new SimpleMDE({
        element: document.getElementById("markdown-editor"),
        spellChecker: false, // 스펠 체크 사용 여부 (옵션)
        toolbar: [
          "bold", "italic", "heading", "|",
          "quote", "code", "unordered-list", "ordered-list", "|",
          "link", "image", "table", "|",
          "preview", "side-by-side", "fullscreen"
        ]
      });
});

function showPopupAndRedirect(redirectUrl) {
    alert("오늘의 경기데이터가 없습니다.");
    setTimeout(function() {
        window.location.href = redirectUrl;
    }, 2000);
}
