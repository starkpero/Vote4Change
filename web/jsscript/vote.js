function addvote()
{
    var id = $('input[type=radio][name=flat]:checked').attr('id');
    data = {candidateid:id}; 
    let xhr = $.post("AddVoteControllerServlet",data,processresponse);
            //xhr.fail(handleError);
}

function processresponse(responseText)
{
        let str = responseText.trim();  
        if(str === "success")
        {
            swal("success","Vote succesfully casted","success").then((value)=>{
            window.location="votingresponse.jsp";
            });
        }
        else
        {
            swal("Error!","Vote couldn't be casted!Try again","error").then((value)=>{
            window.location="votingresponse.jsp";
            });
        }
    
}
    










