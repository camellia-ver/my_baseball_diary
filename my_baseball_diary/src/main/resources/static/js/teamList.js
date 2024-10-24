document.addEventListener('DOMContentLoaded', () => {
    const csrfTokenElement = document.querySelector('input[name="_csrf"]');
    const csrfToken = csrfTokenElement ? csrfTokenElement.value : null;

    if (!csrfToken) {
        console.error('CSRF 토큰을 찾을 수 없습니다.');
        return;
    }

    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(deleteBtn => {
        deleteBtn.addEventListener('click', () => {
            const teamIdInput = deleteBtn.closest('tr').querySelector('input[name="id"]');
            const id = teamIdInput.value;

            var result = confirm("글을 삭제 하시겠습니까?");
            if (!result) {
                return;
            }

            fetch(`/api/baseballTeam/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 올바르지 않습니다.');
                }
                return response.text();
            })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.reload();
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 중 오류가 발생했습니다.');
            });
        });
    });
});
