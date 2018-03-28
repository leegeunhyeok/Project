var permission = 0;
var login = 0;

$(function() {
    $.ajax({
        url: '/process/loginCheck',
        type: 'POST',
        dataType: 'JSON',
        success: function(data){
            if(data.result){
                $('#after-login').removeClass('hidden');
                $('#before-login').addClass('hidden');
                $('#user-id').text(data.result._id + ' 님');
                permission = data.result.permission;
                console.log('login');
                login = 1;
                if(permission) {
                    alert('Hello, Admin');
                }
            } else {
            login = 0;
                $('#before-login').removeClass('hidden');
                $('#after-login').addClass('hidden');
                console.log('no-login');
            }
        }, error: function(err){
            console.log(err.statusText);
        }
    });

    var table = $('#content').DataTable({
        "language": {
            "lengthMenu": "_MENU_ 개씩 보기",
            "zeroRecords": "작성된 글이 없습니다.",
            "info": "_PAGES_중 _PAGE_ 페이지",
            "infoEmpty": "",
            "search": "검색",
            "paginate": {
                "previous": "이전",
                "next": "다음"
            }
        },
        "ajax": {
            "url": "/process/getContent",
            "type": "POST"
         }
    });

    $('#content tbody').on('click', 'tr', function() {
        var _cid = table.row(this).data()[0];
        var _id = table.row(this).data()[3];

        $.ajax({
            url: '/process/getPost',
            type: 'POST',
            dataType: 'JSON',
            data: {'cid': _cid},
            success: function(data) {
                $('#content-title').text(data.title);
                $('#content-main').text(data.content);
                $('#content-id').text(_cid);
                $('#content-date').text(data.date);
                $('#content-writer').text(_id);
                $('#view-modal').modal();
            }, error: function(err) {
                console.log(err.statusText);
            }
        })
    });

    $('#delete-btn').click(function() {
        $.ajax({
            url: '/process/deletePost',
            type: 'POST',
            dataType: 'JSON',
            data: {'cid': $('#content-id').text(), 'id':$('#content-writer').text()},
            success: function(data) {
                console.log(data.message);
                if(data.result) {
                    alert(data.message);
                    location.href = '/';
                } else {
                    alert(data.message);
                }
            },
            error: function(err) {
                console.log(err.statusText);
            }
        });
    });

    $('#edit-btn').click(function() {
        var oldTitle = $('#content-title').text();
        var oldContent = $('#content-main').text();

        $('#modify-title').val(oldTitle);
        $('#modify-content').val(oldContent);
        $('#modify-cid').val($('#content-id').text());
        $('#modify-id').val($('#content-writer').text());

        $('#modify-modal').modal();
    });

    $('#open-write-modal').click(function() {
        if(login) {
            $('#write-modal').modal();
        } else {
            alert('Access denied.');
        }
    });
});