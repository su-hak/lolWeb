document.addEventListener('DOMContentLoaded', function() {
    const pollOptionsContainer = document.getElementById('pollOptions');
    const submitButton = document.getElementById('submitPoll');

    // 두 개 미만의 옵션이 입력된 경우 투표 버튼 비활성화
    function disableSubmitButton() {
        const pollOptions = pollOptionsContainer.querySelectorAll('.poll-option');
        let filledOptions = 0;
        pollOptions.forEach(function(option) {
            if (option.value.trim() !== '') {
                filledOptions++;
            }
        });
        submitButton.disabled = filledOptions < 2;
    }

    // 새로운 투표 옵션 추가
    function addPollOption() {
        const pollOptions = pollOptionsContainer.querySelectorAll('.poll-option');
        // 현재 pollOption의 개수가 최소한 2개인지 확인
        if (pollOptions.length >= 2) {
            const lastInput = pollOptionsContainer.lastElementChild.querySelector('input');
            if (!lastInput || lastInput.value.trim() !== '') {
                const newInput = document.createElement('input');
                newInput.type = 'text';
                newInput.className = 'form-control poll-option';
                newInput.placeholder = '투표 내용을 입력하세요';
                newInput.style.fontSize = '1.3rem';
                pollOptionsContainer.appendChild(newInput);
                disableSubmitButton();
            }
        }
    }

    // 마지막 투표 옵션 제거
    function removeLastPollOption() {
        const pollOptions = pollOptionsContainer.querySelectorAll('.poll-option');
        const lastOption = pollOptions[pollOptions.length - 1];
        if (pollOptions.length > 2) {
            if (lastOption && lastOption.value.trim() === '') {
                const prevOption = lastOption.previousElementSibling;
                lastOption.remove();
                // 이전 투표 옵션에 포커스 설정
                if (prevOption) {
                    prevOption.focus();
                }
            }
        }
    }

    // 투표 옵션에 입력 이벤트 리스너 추가
    pollOptionsContainer.addEventListener('input', function(event) {
        if (event.target.matches('input[type="text"].poll-option')) {
            disableSubmitButton();
            if (event.target === pollOptionsContainer.lastElementChild) {
                addPollOption();
            }
        }
    });

    // Backspace 또는 Delete 키 입력 시 마지막 투표 옵션 제거
    pollOptionsContainer.addEventListener('keydown', function(event) {
        if ((event.key === 'Backspace' || event.key === 'Delete') && event.target.value === '') {
            removeLastPollOption();
            disableSubmitButton();
        }
    });

    // 폼 제출 시 마지막 투표 옵션이 비어 있는지 확인하고 제거
    const form = document.querySelector('.modal-content');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        removeLastPollOption();
        if (checkEmptyInputs()) {
            alert('입력란을 모두 채워주세요!');
        } else {
            // 폼 제출 처리 코드 추가 (예: 서버로 데이터 전송)
            // 예시: sendDataToServer();
        }
    });
});