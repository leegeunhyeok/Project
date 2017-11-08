$(function(){
    var resultText = $('#check-result');
    var submitBtn = $('#submit');
    var temp = false;
    
    $('#back-btn').click(function(){
        location.href = "/"; 
    });
    
    $('#input-id').change(function(){
        temp = false;
        resultText.removeClass('text-danger text-success');
        resultText.html('중복확인을 해주세요');
        submitBtn.removeClass('btn-primary');
        submitBtn.prop('disabled', true);
    });
    
    $('#id-check').click(function(){
        var id = $('#input-id').val();
        
        if(id == ''){
            alert('이메일을 입력해 주세요');
        } else {
            $.ajax({
                url:'/process/idCheck',
                type:'POST',
                data:{'id':id},
                dataType: 'html',
                success: function(data){
                    data = JSON.parse(data);
                    
                    if(data.result == true){
                        temp = true;
                        resultText.html('사용 가능한 이메일입니다');
                        resultText.removeClass('text-danger').addClass('text-success');
                        submitBtn.prop('disabled', false);
                        submitBtn.addClass('btn-primary');
                    } else {
                        temp = false;
                        resultText.html('이미 존재하는 이메일입니다');
                        resultText.removeClass('text-success').addClass('text-danger');
                    }
                }
            });
        }
    });
});


