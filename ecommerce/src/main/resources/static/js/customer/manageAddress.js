$(document).ready(function(){
});

function deleteAddress(id) {
    let result = confirm("Do you want to delete this address?");
    var id = {"id" : id};
    if (result) {
        $.ajax({
            type:'POST',
            url: '/customer/address/delete',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: JSON.stringify(id),
            success : function (result) {
                alert(result.msg);
                $('#reload').submit();
            },
            error: function (result) {
                alert(result.msg);
            },
        });
    } else {

    }
}

