function toJsonData(name,message,status){
    var json={};
    json.name=name;
    json.date=new Date().toLocaleTimeString('chinese',{hour12:false});
    json.message=message;
    json.status=status;
    return JSON.stringify(json);
}
function doubleclick(user1,user2){
        window.open("/chat?user1="+user1+"&user2="+user2,"_blank");
}
$(function (){
    feather.replace();
    //绑定按钮
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            $("#button").click();
        }
    })

})