
var products = {} || products;
products.intTable = function(){
    $("#products-datatables").DataTable({
        ajax: {
            url: 'http://localhost:8080/api/products/',
            method: "GET",
            datatype: "json",
            dataSrc: ""
        },
        columns: [
            { data: "name" , name: "Name", title: "Name",defaultContent: "",orderable: true},
            { data: "email", name : "Email" , title: "Email",defaultContent: ""},
            { data: "address", name : "Address" , title: "Address",defaultContent: ""},
            { data: "tel", name : "Tel" , title: "Tel",defaultContent: ""},
            { data: "room", name : "Room" , title: "Room",defaultContent: ""},
            { data: "id", name : "Action", title : "Action",sortable: false,
                orderable: false ,"render": function ( data, type, row, meta ) {
                    var str =  "<a href='javascript:;' title='edit product' onclick='products.get("+ data +")'><i class='fa fa-edit'></i></a> " +
                        "<a href='javascript:;' title='remove product' onclick='products.delete("+ data +")' ><i class='fa fa-trash'></i></a>";
                    return str ;
                }
            }
        ]
    });
};

products.addNew = function(){
    $('#modalTitle').html("Add new product");
    products.resetForm();
    $('#modalAddEdit').modal('show');
};
products.initValidation =  function(){
    $("#modalAddEdit").validate({
        rules: {
            name: "required",
            email: {
                required: true,
                Regex: "^[0-9]"
            },
            address:"required",
            tel: {
                required: true,
                Regex: "^[0-9]{10}"
            }
        },
        messages: {
            name: "Please enter your name",
            email: {
                required:"Please enter your email",
                Regex:"Please enter tel"
            },
            address:"Please enter your address",
            tel:{
                required:"Please enter your tel",
                Regex:"Please enter 10 tels character"
            }
        }
    });
};

products.resetForm =  function(){
    $('#formAddEdit')[0].reset();
    $('#name').val('');
    $('#email').val('');
    $('#address').val('');
    $('#tel').val('');
    $('#room').val('');
    //
    var validator = $( "#formAddEdit" ).validate();
    validator.resetForm();
};




products.get = function(id){
    console.log('get :'+ id);

    $.ajax({
        url : "http://localhost:8080/api/products/" + id,
        method : "GET",
        dataType : "json",
        success : function(data){
            console.log(data);
            $('#formAddEdit')[0].reset();
            //
            $('#modalTitle').html("Edit product");
            $('#name').val(data.name);
            $('#email').val(data.email);
            $('#address').val(data.address);
            $('#tel').val(data.tel);
            $('#room').val(data.room);

            $('#id').val(data.id);

            $('#modalAddEdit').modal('show');
        }
    });
};

products.delete = function(id){
    bootbox.confirm({
        title: "Remove product",
        message: "Do you want to remove this product?",
        buttons: {
            cancel: {
                label: '<i class="fa fa-times"></i> No'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Yes'
            }
        },
        callback: function (result) {
            if(result){
                $.ajax({
                    url : "http://localhost:8080/api/products/" + id,
                    method: "DELETE",
                    dataType : "json",
                    success : function(data){
                        $("#reload").location.reload();
                    }
                });
            }
        }
    })
};

products.save = function(){
    if ($("#formAddEdit").valid()){
        if($('#id').val() == 0){
            var productObj = {};
            productObj.name = $('#name').val();
            productObj.email = $('#email').val();
            productObj.address =$('#address').val();
            productObj.tel =$('#tel').val();
            productObj.room = $('#room').val();
            //
            $.ajax({
                url : "http://localhost:8080/api/products/",
                method : "POST",
                dataType : "json",
                contentType : "application/json",
                data : JSON.stringify(productObj),
                done: function(){
                    console.log("POST DONE");
                    $('#modalAddEdit').modal('hide');

                    $("#products-datatables").DataTable().ajax.reload();
                },
                success : function(data){
                    console.log("POST success");
                    $('#modalAddEdit').modal('hide');
                    $("#products-datatables").DataTable().ajax.reload();

                }
            });
        }
        else{
            var productObj = {};
            productObj.name = $('#name').val();
            productObj.email = $('#email').val();
            productObj.address = $('#address').val();
            productObj.tel = $('#tel').val();
            productObj.room = $('#room').val();

            productObj.id = $('#id').val();

            $.ajax({
                url : "http://localhost:8080/api/products/" + productObj.id,
                method : "PUT",
                dataType : "json",
                contentType : "application/json",
                data : JSON.stringify(productObj),
                success : function(data){
                    $('#modalAddEdit').modal('hide');
                    $("#products-datatables").DataTable().ajax.reload();
                }
            });
        }
    }

};

products.init = function(){
    products.intTable();
    products.initValidation();
};

$(document).ready(function(){
    products.init();
});
