$(document).ready(function () {
    $("#selectTariff").change(updateOptions);

    updateOptions();
});

function updateOptions() {
    var selectedTariff = $('#selectTariff option:selected');
    var tariffInfo = $('#tariffInfo');
    var availableOptions = $('#availableOptions');
    var tariffId = $('#selectTariff').val();

    tariffInfo.empty();
    tariffInfo.html("<p>Name: " + selectedTariff.text() + "<br/>Price: " + selectedTariff.attr('data-tariff-price') + "</p>");

    $.ajax({
        url:"/DeltaCom/manager/getOptionsForContract",
        contentType: "application/json",
        data: {
            "selectTariff" : tariffId
        },
        success: function(data) {
            var optionsDrop = $('#selectOptions');
            var optionsList = '';
            var optionsInfo = '';
            $.each(data, function (index, item) {
                optionsList += '<option value="' + item.id + '">' + item.name + '</option>';
                optionsInfo += "<p>Name: " + item.name + "<br/>Price: " + item.price + "<br/>Connection cost: " + item.connectionCost + "</p>";
            });
            optionsDrop.empty();
            optionsDrop.append(optionsList);
            optionsDrop.selectpicker('refresh');
            availableOptions.empty();
            availableOptions.append(optionsInfo);
        },
        error: function () {
            $('#selectOptions').empty();
            availableOptions.empty();
        }
    });
}