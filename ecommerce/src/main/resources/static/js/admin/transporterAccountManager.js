$(document).ready(function () {
    $('#dtBasicExample').DataTable();
    $('.dataTables_length').addClass('bs-select');
});

function deleteUser(userId) {
    let result = confirm("Do you want to delete this user?");
    var userId = {"userId" : userId};
    if (result) {
        $.ajax({
            type:'POST',
            url: '/admin/account/transporter/delete',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: JSON.stringify(userId),
            success : function (result) {
                // $('#reload').submit();
                $("#content").html(result.responseText);
            },
            error: function (result) {
                $("#content").html(result.responseText);
            },
        });
    } else {

    }
}