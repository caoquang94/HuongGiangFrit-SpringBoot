var feedBacks = {} || feedBacks;

feedBacks.initValidation = function () {
    $("#modalAddEdit").validate({
        rules: {
            name: "required",
            title: {
                required: true,
            },
            email: "required",
            description: {
                required: true,

            }
        },
        messages: {
            name: "Please enter your name",
            title: {
                required: "Please enter your title",

            },
            email: "Please enter your email",
            description: {
                required: "Please enter your description",

            }
        }
    });
};

feedBacks.resetForm = function () {
    $('#formAddEdit')[0].reset();
    $('#name').val('');
    $('#title').val('');
    $('#email').val('');
    $('#description').val('');
    //
    var validator = $("#formAddEdit").validate();
    validator.resetForm();
};


feedBacks.get = function (id) {
    console.log('get :' + id);

    $.ajax({
        url: globlaConfig.url +"api/feedBacks/" + id,
        method: "GET",
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#formAddEdit')[0].reset();
            //
            $('#modalTitle').html("Edit feedBack");
            $('#name').val(data.name);
            $('#title').val(data.title);
            $('#email').val(data.email);
            $('#description').val(data.description);
            $('#id').val(data.id);

            $('#modalAddEdit').modal('show');
        }
    });
};


feedBacks.save = function () {
    if ($("#formAddEdit").valid()) {
        if ($('#id').val() == 0) {
            var feedBackObj = {};
            feedBackObj.name = $('#name').val();
            feedBackObj.email = $('#email').val();
            feedBackObj.title = $('#title').val();
            feedBackObj.description = $('#description').val();
            $('#modalTitle').html("Message");
            $('#modalSuccess').modal('show');
            //
            $.ajax({
                url: globlaConfig.url +"feedback/",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(feedBackObj),
            });
        } else {
            var feedBackObj = {};
            feedBackObj.name = $('#name').val();
            feedBackObj.email = $('#email').val();
            feedBackObj.title = $('#title').val();
            feedBackObj.description = $('#description').val();
            feedBackObj.id = $('#id').val();

            $.ajax({
                url: globlaConfig.url +"api/feedBacks/" + feedBackObj.id,
                method: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(feedBackObj),
                success: function (data) {
                    $('#modalAddEdit').modal('hide');
                    window.location.href = 'http://localhost:8080/user/index';
                }
            });
        }
    }
};

feedBacks.success = function(){
    window.location.href = globlaConfig.url + "user/index";
}
feedBacks.init = function () {
    feedBacks.initValidation();
};

$(document).ready(function () {
    feedBacks.init();
});
