document.addEventListener('DOMContentLoaded', function() {
       const teamSelect = document.getElementById('teamSelect');
       const signupForm = document.getElementById('signupForm');
       const signupBtn = document.getElementById('signupBtn');

       signupForm.addEventListener('submit', function(event) {
       if (teamSelect.value === "") {
               event.preventDefault();
               document.getElementById('teamSelectError').innerText = "팀을 선택해 주세요.";
            }
       });
});