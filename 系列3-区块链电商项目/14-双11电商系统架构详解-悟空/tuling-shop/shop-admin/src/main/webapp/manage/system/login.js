$(function(){
    if (top.location != self.location) {
        top.location = self.location;
    }
    $("#username").focus();
    $("#btnLogin").click(function(){
        $("#formLogin").submit();
    });
});