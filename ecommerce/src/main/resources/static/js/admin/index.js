$(document).ready(function(){
});

function deleteUser(userId) {
    let result = confirm("Do you want to delete this user?");
    var userId = {"userId" : userId};
    if (result) {
        $.ajax({
            type:'POST',
            url: '/admin/account/delete',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: JSON.stringify(userId),
            success : function (result) {
                $('#reload').submit();
            },
            error: function (result) {
                console.log(result);
            },
        });
    } else {

    }
}

