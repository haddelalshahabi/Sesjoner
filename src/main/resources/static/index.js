function loggInn(){
    const url ="/loggInn?brukernavn="+$("#brukernavn").val()+ "&passord="+$("#passord").val();
    $.get(url, function (Ok){
        if (Ok){
            window.location.href = "liste.html";
        } else {
            $("#feil").html("Feil brukernavn eller passord");
        }
    })
        .fail(function (jqXHR){
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message)
        });
}