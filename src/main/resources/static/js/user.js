let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-login").on("click", ()=>{
            this.login();
        });
    },
    
    save: function() {
        let data = {
            userName: $("#userName").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(response){
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error))
        });
    },

    login: function() {
        let data = {
            userName: $("#userName").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(response){
            alert("로그인 되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error))
        });
    },
}

index.init();