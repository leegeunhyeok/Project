$(function() {
    var table = $('#user-list').DataTable({
        "language": {
            "lengthMenu": "_MENU_ 개씩 보기",
            "zeroRecords": "가입한 유저가 없습니다.",
            "info": "_PAGES_중 _PAGE_ 페이지",
            "infoEmpty": "",
            "search": "검색",
            "paginate": {
                "previous": "이전",
                "next": "다음"
            }
        },
        "ajax": {
            "url": "/process/getUser",
            "type": "POST"
         }
    });
});

