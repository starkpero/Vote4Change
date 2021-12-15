

let userid,password;
function connectUser()
{
    userid = $("#username").val();
    password = $("#password").val();
    if(validateUser()===false)
    {
        swal("Access Denied","Please enter userid and password","Error");
        return;
        
    }
    let data ={
            userid : userid,
            password: password
        };
        let xhr = $.post("LoginControllerServlet",data,processResponse);
        xhr.fail(handleError);
}

function processResponse(responseText)
{
    if(responseText.trim()==='error')
    {
        swal("Access Denied!","Invalid userid/password","error");
    }
    else if(responseText.trim().indexOf("jsessionid")!==-1)
    {
        let pr = swal("Success","Login successful","success");
        //pr.then(fun1,fun2); // then function won't let execution of fun1 until above code gets executed
        //First argument is called when there is no problem in above code
        //Second runs when there is some problem with above code 
        //primarily then() function is made for server communication
        pr.then(value =>{
            window.location = responseText.trim();
        }); //Since no error would be generated in swal we don't need second argument 
        
    }
    else
    {
        swal("Access Denied","Some problem occured:"+responseText,"error");
        //return;
    }
}

//function fun1(value) //value indicates true,fun2 argument would have indicated false
//{
//    window.location = responseText.trim();
//}

function handleError(xhr)
{
    swal("Error","Problem in server communication" + xhr.statusText,"error");
}

function validateUser()
{
    if(userid ===""||password==="")
    {
        swal("Error!","All fields are mandatary","error");
        return false;
    }
    return true;
}
