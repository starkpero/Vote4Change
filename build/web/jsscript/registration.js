/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let username,password,cpassword,email,city,adress,adhar,mobile;
function addUser()
{
    username = $("#username").val();
    password = $("#password").val();
    cpassword = $("#cpassword").val();
    city = $("#city").val();
    address = $("#address").val();
    adhar = $("#adhar").val();
    email = $("#email").val();
    mobile = $("#mobile").val();
    if(validateUser())
    {
        if(password!==cpassword)
        {
            swal("Error!","Passwords do not match","error");
            return;
        }
        else
        {
            if(checkEmail()===false)
                return;
            if(checkMobileno()===false)
                return;
            let data = {
                username : username,
                password : password,
                city : city,
                address : address,
                userid : adhar,
                email : email,
                mobile : mobile
            };
            let xhr = $.post("RegistrationControllerServlet",data,processresponse);
            xhr.fail(handleError);
        }
    }
    
}
function processresponse(responseText,textStatus,xhr)
{
        let str = responseText.trim();  //uap,success,error will come in responsetext
        if(str === "success")
        {
            swal("success","Registration done successfully! You can now login","success");
            setTimeout(redirectUser,4000);
        }
        else if(str === "uap")
            swal("Error!","User already present","error");
        else
            swal("Error!","Registration failed!Try again","error");
}

function handleError(xhr)
{
    swal("Error","Problem in server communication" + xhr.statusText,"error"); 
}


function validateUser()
{   
    //could also use isEmpty() method of String 
    if(username===""||password===""||cpassword===""||city===""||address===""||adhar===""||email===""||mobile==="")
    {
        swal("Error!","All fields are mandatary","error");
        return false; //Ajax request won't be generated 
    }
    return true;
}

function checkEmail()
{
    let attheratepos = email.indexOf("@");
    let dotpos = email.indexOf(".");
    if(attheratepos<1||dotpos<attheratepos+2||dotpos+2>=email.length)
    {
        swal("Error!","Please enter a valid email","error");
        return false;
    }
    return true;
}

function checkMobileno()
{
    if(isNaN(mobile)|| mobile.indexOf(" ")!==-1)
    {
        swal("Error!","Invalid mobile number","error");
        return false;
    }
    if(mobile.length!==10)
    {
        swal("Error!","Please enter a valid 10 digit mobile number","error");
        return false;
    }
    return true;
}

function redirectUser()
{
    window.location = "login.html";
}