$(document).ready(function () {
    $("input").rating();
    $('#rate').rating({
        count:5,
        size:20,
        primaryColor:"#F4F4F4",
        selectedcolor:"#fdb300",
        scale:10,
        readonly:"false",
        rate:0
    })
})
