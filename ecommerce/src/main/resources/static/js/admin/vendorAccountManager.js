$(document).ready(function (){
    $('input[type=radio][name=exampleRadios]').change(function() {
        var data;
        if (this.value == 'all') {
            data = { 'type':'all'}
        }
        else if (this.value == 'deactivated') {
            data = { 'type':'deactivated'}
        }
        else if (this.value == 'active') {
            data = { 'type':'active'}
        };

        $.get("/admin/account/vendor",data).done(function(fragment) {
            $("#content").html(fragment);
        });
    });
})

function activeAccount(vendorId, enable)
{
    var radioType = $('input[type=radio][name=exampleRadios]:checked').val();
    let result = confirm("Do you want to approve this user?");
    var vendorForm = {
        "vendorId" : vendorId,
        "enable" : enable,
        "radioType" : radioType
    };
    if (result) {
        // $.post("/admin/account/vendor/active", vendorForm).done(function(fragment) {
        //     $("#content").html(fragment);
        // });
        $.ajax({
            type:'POST',
            url: '/admin/account/vendor/active',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: JSON.stringify(vendorForm),
            success : function (result) {
                // alert(result.responseText)
                // $('#reload').submit();
                // $.get("/admin/account/vendor");
                $("#content").html(result.responseText);
            },
            error: function (result) {
                // alert(result.responseText);
                // $('#reload').submit();
                $("#content").html(result.responseText);
            },
        });
    } else {

    }
}

function deleteUser(userId) {
    let result = confirm("Do you want to delete this user?");
    var userId = {"userId" : userId};
    if (result) {
        $.ajax({
            type:'POST',
            url: '/admin/account/vendor/delete',
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