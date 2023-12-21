function validateAndSubmit() {
    let checkPick = document.querySelector('select[name="searchType"]').value;
    let inputKeyword = document.querySelector('input[name="searchKeyword"]').value;
    if (checkPick === 'none') {
        alert('목록을 선택해주세요.');
        return;
    }
    if (inputKeyword.trim() === '') {
        alert('검색할 단어를 입력해주세요.');
        return;
    }
    if (checkPick === 'no' && !/^\d+$/.test(inputKeyword)) {
        alert('게시글 번호를 입력해주세요.');
        return;
    }
    document.forms[0].submit();
}