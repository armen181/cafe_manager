$(document).ready(function () {

    // ================== Read All CafeTables and write into tree===============
    $('body').on("click", ".tableList", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/tableGet",
            "method": "GET",
            "headers": {}
        };

        $.ajax(settings).done(function (response) {
            console.log(response);
            tableList(response);
        });
    });






    //===========  open  Order Sign model to Table ====================
    $('body').on("click", ".addOrder", function (e) {
        $('#creatAndAddOrderToTableId').text(e.currentTarget.value);
        $('#creatAndAddOrderToTable').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  Table Sign model to User ====================
    $('body').on("click", "#creatAndAddOrderToTableBtn", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/orderCreat",
            "method": "GET",
            "headers": {
                "name": $('#creatAndAddOrderToTableId').val()
            }
        };


        $.ajax(settings).done(function (response) {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/rest/tableSignOrder",
                "method": "GET",
                "headers": {
                    "cafeTableId": $('#creatAndAddOrderToTableId').text(),
                    "tableOrderId": response.id
                }
            };


            $.ajax(settings).done(function (response2) {

                $('#creatAndAddOrderToTable').modal('hide');

            });


        });

    });


    //===========  open  Order delete model ====================
    $('body').on("click", ".deleteOrder", function (e) {
        $('#orderNameForRemove').text(e.currentTarget.value);
        $('#removeOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  open  Order delete model ====================
    $('body').on("click", ".deleteProductInOrder", function (e) {
        $('#ProductInOrderForRemove').text(e.currentTarget.value);
        $('#removeProductInOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  Order remove model ====================
    $('body').on("click", "#removeOrder", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/orderRemoveById",
            "method": "GET",
            "headers": {
                "id": $('#orderNameForRemove').text()
            }
        };
        $.ajax(settings).done(function (response) {
            $('#removeOrder').modal('hide');
        });


    });

    //===========  open  Order edit model ====================
    $('body').on("click", ".editOrder", function (e) {
        $('#orderIdForEdit').text(e.currentTarget.value);
        $('#editOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  Order edit model ====================
    $('body').on("click", "#orderEditButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/orderEditById",
            "method": "GET",
            "headers": {
                "id": $('#orderIdForEdit').text(),
                "status": $('#orderEditStatus').val()
            }
        };
        $.ajax(settings).done(function (response) {
            $('#editOrder').modal('hide');
        });


    });

    //===========  open  Product in order to Order ====================
    $('body').on("click", ".addProductInOrder", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productGetAll",
            "method": "GET",
            "headers": {
            }
        };

        $.ajax(settings).done(function (response) {
            var html = "";
            html +="<select class=\"form-control chosen-select btn-block\" id=\"addProductInOrderToProduct\">";
            response.forEach(function (x)
            {
                html += "<option value=\"" + x.id + "\">\n" +
                    "       " + x.name + "\n" +
                    "                                    </option>";
            });
            $('#addProductInOrderToProduct').replaceWith(html + "</select>");
        });

        $('#addProductInOrderId').text(e.currentTarget.value);
        $('#addProductInOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');

    });
//===========  open  Product in order to Order ====================
    $('body').on("click", "#ProductInOrderAdd", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productInOrderCreate",
            "method": "GET",
            "headers": {
                "amount": $('#nameProductInOrder').val(),
                "status": $('#productInOrderStatus').val()
            }
        };


        $.ajax(settings).done(function (response) {




            var settings1 = {
                "async": true,
                "crossDomain": true,
                "url": "/rest/productInOrderSignProduct",
                "method": "GET",
                "headers": {
                    "productId": $('#addProductInOrderToProduct').val(),
                    "praductinorderid": response.id,
                    "amount":response.amount
                }
            };


            $.ajax(settings1).done(function (response2) {
                var settings2 = {
                    "async": true,
                    "crossDomain": true,
                    "url": "/rest/orderSignToProductInOrder",
                    "method": "GET",
                    "headers": {
                        "orderId": $('#addProductInOrderId').text(),
                        "productInOrderId": response.id,
                    }
                };


                $.ajax(settings2).done(function (response2) {
                    console.log(response2);

                    $('#addProductInOrder').modal('hide');

                });
            });


        });
    });

    //===========  open  Order delete model ====================
    $('body').on("click", ".deleteProductInOrder", function (e) {
        $('#ProductInOrderForRemove').text(e.currentTarget.value);
        $('#removeProductInOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  Order remove model ====================
    $('body').on("click", "#ProductInOrderButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productInOrderRemove",
            "method": "GET",
            "headers": {
                "id": $('#ProductInOrderForRemove').text()
            }
        };
        $.ajax(settings).done(function (response) {
            $('#removeProductInOrder').modal('hide');
        });


    });

    //===========  open  Order edit model ====================
    $('body').on("click", ".editProductInOrder", function (e) {
        $('#editProductInOrderName').text(e.currentTarget.value);
        $('#editProductInOrder').modal({
            backdrop: 'static',
            keyboard: false
        }, 'show');


    });

    //===========  Order edit model ====================
    $('body').on("click", "#editProductInOrderButton", function (e) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/rest/productInOrderEdit",
            "method": "GET",
            "headers": {
                "id": $('#editProductInOrderName').text(),
                "amount": $('#editProductInOrderAmount').val(),
                "status": $('#editProductInOrderStatus').val()
            }
        };
        $.ajax(settings).done(function (response) {
            $('#editProductInOrder').modal('hide');
        });


    });

});

function tableList(response) {
    var html = "<div id=\"table\">\n";

    response.forEach(function (x) {

        html+="        <nav class=\"navbar bg-white navbar-light\">\n"+
            "<div class=\"row\">\n" +
            "                <button class=\"navbar-toggler btn-info\" type=\"button\" >\n" +
            "                    "+ x.id + "\n " +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                    "+ x.name + "\n" +
            "                </button>\n";

        if(x.userName == null){
            html+="</div>"
        }    else {
            html+="                <button class=\"navbar-toggler btn-primary\" type=\"button\" >\n" +
                "                    " + x.userName  +
                "                </button></div>"
        }

            html += "<div class=\"row\">\n" +
                "                <button class=\"navbar-toggler btn-info addOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Add Order\n" +
                "                </button>\n";


        html+= "            <button class=\"navbar-toggler collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#x"+x.id + "_"  + x.userName+"\" aria-expanded=\"false\">\n" +
            "                <span class=\"navbar-toggler-icon\"></span>\n" +
            "            </button>\n" +
            "\n" +
            "            </div>"

        html+="<div class=\"navbar-collapse collapse\" id=\"x"+  x.id + "_"  + x.userName+"\" style=\"\">\n";

        html+=orderListUL(x.tableOrders);


        html+= "            </div>\n" +
            "        </nav>";

    });
    $('#table').replaceWith( html );
}
function orderListUL(response) {
    var html = "";
    response.forEach(function (x) {

        html+="<ul class=\"nav navbar-nav\">\n" +
            "                    <li class=\"nav-item\">"+
            "<nav class=\"navbar bg-white navbar-light\">"+
            "<div class=\"row\">\n" +
            "                <button class=\"navbar-toggler btn-info\" type=\"button\" >\n" +
            "                    "+ x.id + "\n " +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                    "+ x.name + "\n" +
            "                </button>\n"+
            "                <button class=\"navbar-toggler btn-primary\" type=\"button\" >\n" +
            "                    " + x.orderStatus  +
            "                </button></div>"


        if(x.orderStatus === "CLOSE"){
            html += "<div class=\"row\">\n" +
                "                <button class=\"navbar-toggler btn-danger deleteOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Delete\n" +
                "                </button>\n" ;
        }else {
            html += "<div class=\"row\">\n" +
                "                <button class=\"navbar-toggler btn-info addProductInOrder\" type=\"button\" value=\"" +x.id+"\">\n" +
                "                    Add Products\n" +
                "                </button>\n" +
                "                <button class=\"navbar-toggler btn-secondary editOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Edit\n" +
                "                </button>\n" +
                "                <button class=\"navbar-toggler btn-danger deleteOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Delete\n" +
                "                </button>\n" ;

        }

        html+= "            <button class=\"navbar-toggler collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#y"+x.id + "_"  + x.userName+"\" aria-expanded=\"false\">\n" +
            "                <span class=\"navbar-toggler-icon\"></span>\n" +
            "            </button>\n" +
            "\n" +
            "            </div>"

        html+="<div class=\"navbar-collapse collapse\" id=\"y"+  x.id + "_"  + x.userName+"\" style=\"\">\n" ;

        html+=productInOrderUL(x.productInOrders);

        html+= "            </div>\n" +
            "        </nav></li>";

    });
    html+="</ul>";
    return html;
}
function productInOrderUL(response) {
    var html = "";
    response.forEach(function (x) {

        html+="<ul class=\"nav navbar-nav\">\n" +
            "                    <li class=\"nav-item\">"+
            "<nav class=\"navbar bg-white navbar-light\">"+
            "<div class=\"row\">\n" +
            "                <button class=\"navbar-toggler btn-info\" type=\"button\" >\n" +
            "                    "+ x.product.name + "\n " +
            "                </button>\n" +
            "                <button class=\"navbar-toggler btn-secondary\" type=\"button\" >\n" +
            "                    "+ x.status  + "\n" +
            "                </button>\n"+
            "                <button class=\"navbar-toggler btn-primary\" type=\"button\" >\n" +
            "                    " + x.amount  +
            "                </button></div>"


        if(x.orderStatus === "CLOSE"){
            html += "<div class=\"row\">\n" +
                "                <button class=\"navbar-toggler btn-danger deleteProductInOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Delete\n" +
                "                </button>\n" ;
        }else {
            html += "<div class=\"row\">\n" +
                "                <button class=\"navbar-toggler btn-secondary editProductInOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Edit\n" +
                "                </button>\n" +
                "                <button class=\"navbar-toggler btn-danger deleteProductInOrder\" type=\"button\" value=\""+x.id+"\">\n" +
                "                    Delete\n" +
                "                </button>\n" ;

        }

        html+= "            <button class=\"navbar-toggler collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#z"+x.id + "_"  + x.status+"\" aria-expanded=\"false\">\n" +
            "                <span class=\"navbar-toggler-icon\"></span>\n" +
            "            </button>\n" +
            "\n" +
            "            </div>"

        html+="<div class=\"navbar-collapse collapse\" id=\"z"+  x.id + "_"  + x.status+"\" style=\"\">\n" +
            "                <ul class=\"nav navbar-nav\">\n" +
            "                    <li class=\"nav-item\">\n" +
            "                        <a class=\"nav-link\" >"+ x.product.id+"</a>\n" +
            "                    </li>\n" +
            "                    <li class=\"nav-item\">\n" +
            "                        <a class=\"nav-link\" >"+ x.product.name+"</a>\n" +
            "                    </li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "        </nav></li>";

    });
    html+="";
    return html;
}