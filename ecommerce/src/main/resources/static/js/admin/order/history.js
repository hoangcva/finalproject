function updateOrder(id, orderDspId, orderStatus) {
    var orderForm = {
        "id": id,
        "orderDspId": orderDspId,
        "orderStatus": orderStatus
    };
    $.ajax({
        type: 'GET',
        url: '/admin/orders/update',
        // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
        contentType: 'application/json',
        dataType: 'json',
        data: orderForm,
        // success : function (result) {
        //     $('#reload').submit();
        // },
        // error: function (result) {
        //     console.log(result);
        // },
    }).always(function (result) {
        $("#content").html(result.responseText);
        $('#dtBasicExample').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
}
