var conditionFlg = 0;
$(document).ready(function(){
    setCitySelect();
});

function setCitySelect() {
    $.ajax({
        url: '/showCategory',
        type: 'GET',
        success: function (data) {
            data.forEach(function (category) {
                $('#category-select')
                    .append($("<option></option>")
                        .attr("value", category.id)
                        .text(category.name));
            });
            customSelectbox(1);
            $('.select-items').children().on('click', function () {
                var city = $(this).html();
                conditionFlg = 1;
                setDistrictSelect(city);
            });
        }
    });
}

function setDistrictSelect(city) {
    $.ajax({
        url: '/showSubCategory',
        type: 'GET',
        success: function (data) {
            $(".custom-select2 option[value!='0']").remove();
            data.forEach(function (store) {
                if (store.city == city) {
                    $('#district-select')
                        .append($("<option></option>")
                            .text(store.district));
                };
            });
            $('.custom-select2>div').remove();
            customSelectbox(2);
            $('.custom-select2 .select-items').children().on('click', function () {
                var district = $(this).html();
            });
        }
    });
}

function customSelectbox(selectFlg) {
    var x, i, j, selElmnt, a, b, c;
    /*look for any elements with the class "custom-select":*/
    if (selectFlg == 1) {
        x = document.getElementsByClassName("custom-select");
    } else {
        x = document.getElementsByClassName("custom-select2");
    }

    for (i = 0; i < x.length; i++) {
        selElmnt = x[i].getElementsByTagName("select")[0];
        /*for each element, create a new DIV that will act as the selected item:*/
        a = document.createElement("DIV");
        a.setAttribute("class", "select-selected");
        a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
        x[i].appendChild(a);
        /*for each element, create a new DIV that will contain the option list:*/
        b = document.createElement("DIV");
        b.setAttribute("class", "select-items select-hide");
        for (j = 1; j < selElmnt.length; j++) {
            /*for each option in the original select element,
            create a new DIV that will act as an option item:*/
            c = document.createElement("DIV");
            c.innerHTML = selElmnt.options[j].innerHTML;
            c.addEventListener("click", function (e) {
                /*when an item is clicked, update the original select box,
                and the selected item:*/
                var y, i, k, s, h;
                s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                h = this.parentNode.previousSibling;
                for (i = 0; i < s.length; i++) {
                    if (s.options[i].innerHTML == this.innerHTML) {
                        s.selectedIndex = i;
                        h.innerHTML = this.innerHTML;
                        y = this.parentNode.getElementsByClassName("same-as-selected");
                        for (k = 0; k < y.length; k++) {
                            y[k].removeAttribute("class");
                        }
                        this.setAttribute("class", "same-as-selected");
                        break;
                    }
                }
                h.click();
            });
            b.appendChild(c);
        }
        x[i].appendChild(b);
        a.addEventListener("click", function (e) {
            /*when the select box is clicked, close any other select boxes,
            and open/close the current select box:*/
            e.stopPropagation();
            closeAllSelect(this);
            this.nextSibling.classList.toggle("select-hide");
            this.classList.toggle("select-arrow-active");
        });
    }
    function closeAllSelect(elmnt) {
        /*a function that will close all select boxes in the document,
        except the current select box:*/
        var x, y, i, arrNo = [];
        x = document.getElementsByClassName("select-items");
        y = document.getElementsByClassName("select-selected");
        for (i = 0; i < y.length; i++) {
            if (elmnt == y[i]) {
                arrNo.push(i)
            } else {
                y[i].classList.remove("select-arrow-active");
            }
        }
        for (i = 0; i < x.length; i++) {
            if (arrNo.indexOf(i)) {
                x[i].classList.add("select-hide");
            }
        }
    }
    /*if the user clicks anywhere outside the select box,
    then close all select boxes:*/
    document.addEventListener("click", closeAllSelect);
}