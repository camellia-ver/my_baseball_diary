document.addEventListener('DOMContentLoaded', () => {
    const csrfTokenElement = document.querySelector('input[name="_csrf"]');
    const csrfToken = csrfTokenElement ? csrfTokenElement.value : null;

    if (!csrfToken) {
        console.error('CSRF 토큰을 찾을 수 없습니다.');
        return;
    }

    const deleteBtn = document.getElementById('delete-btn');
    const diaryIdInput = document.getElementById('diary-id');

    if (deleteBtn) {
        deleteBtn.addEventListener('click', () => {
            var result = confirm("글을 삭제 하시겠습니까?");
            if (!result)
            {
                return
            }

            const id = diaryIdInput.value;
            if (!id) {
                alert('유효한 ID를 입력해 주세요.');
                return;
            }

            fetch(`/api/diaries/${id}`, {
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
                location.replace('/main');
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 중 오류가 발생했습니다.');
            });
        });
    }
});
