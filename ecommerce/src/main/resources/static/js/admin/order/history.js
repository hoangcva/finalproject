function cancelOrder(id, orderDspId, orderStatus) {
    var orderForm = {  "id" : id,
                "orderDspId" : orderDspId,
                "orderStatus" : orderStatus
            };
    $.ajax({
        type:'POST',
        url: '/admin/order/update',
        // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
        contentType: 'application/json',
        dataType : 'json',
        data: JSON.stringify(orderForm),
        // success : function (result) {
        //     $('#reload').submit();
        // },
        // error: function (result) {
        //     console.log(result);
        // },
    });
}