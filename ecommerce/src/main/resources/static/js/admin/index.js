$(document).ready(function(){
});

function deleteUser(userName) {
    let result = confirm("Do you want to delete this user?");
    // var userName = $(this).attr('data-userName');
    if (result) {
        $.ajax({
            type:'DELETE',
            url: '/admin/delete',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: {"userName" : userName},
            success : function (result) {
                console.log(result);
            },
            error: function (result) {
                console.log(result);
            },
            complete: function () {

            }
        });
    } else {

    }
}

