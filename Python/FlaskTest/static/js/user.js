$(function() {
    var _id = '';
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

    $('#user-list tbody').on('click', 'tr', function() {
        _id = table.row(this).data()[0];
        $('#popup').modal();
    });

    $('#delete-btn').click(function() {
        $.ajax({
            url: '/process/deleteUser',
            type: 'POST',
            dataType: 'JSON',
            data: {'id': _id},
            success: function(data) {
                if(data.result) {
                    alert(_id + ' 유저 삭제완료');
                } else {
                    alert('삭제 실패');
                }
                location.href = '/user';
            }, error: function(err) {
                console.log(err.statusText);
            }
        });
    });
});

