function randint(min, max){
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
function pickOne(array){
  return array[randint(0, array.length-1)];
}


function newPassword(){
  var password = "";
  for(var i=0; i<20; i++)
    password += pickOne("azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN,;:!?./§*ù^$¨£µ%&é\"\'(-è_çà)=1234567890°+");
  return password;
}


/* Manipulation du DOM */

function search_for_id(node){
  //On cherche récursivement l'input du login.
  //Ce doit être un input non hidden
  if(node.nodeName.toLowerCase() == "input" && node.getAttribute("type") != "hidden" && node.getAttribute("type") != "password")
    return node;

  for(var i=0; i<node.childNodes.length; i++){
    var res = search_for_id(node.childNodes[i]);
    if(res)
      return res;
  }

  return false;
}

function search_for_submit(node){
  //On cherche récursivement l'input du login.
  //Ce doit être un input non hidden
  if(node.nodeName.toLowerCase() == "input" && node.getAttribute("type") == "submit")
    return node;

  for(var i=0; i<node.childNodes.length; i++){
    var res = search_for_id(node.childNodes[i]);
    if(res)
      return res;
  }

  return false;
}

function search_for_password_confirmation(node, passwordInput){
  //On cherche récursivement l'input du password de confirmation.
  //Ce doit être un input non hidden
  if(node.nodeName.toLowerCase() == "input" && node.getAttribute("type") == "password" && node != passwordInput)
    return node;

  for(var i=0; i<node.childNodes.length; i++){
    var res = search_for_password_confirmation(node.childNodes[i], passwordInput);
    if(res)
      return res;
  }

  return false;
}

/* Les fonctions du plugin */
function find_password_form(){

  //On cherche un input de password
  var passwordInput = document.querySelector('input[type=password]');
  if(!passwordInput)
    return {"type": false};

  //On récupère la form associée
  var formField = passwordInput;
  var attempts = 50;
  while(formField.nodeName.toLowerCase() != "form" && attempts > 0){
    formField = formField.parentNode;
    attempts --;
  }

  //On récupère le login associé, et le bouton de validation
  var loginInput = search_for_id(formField);
  var submit = search_for_submit(formField);

  // On vérifie s'il y a confirmation de password (inscription)
  var passwordConfirmation = search_for_password_confirmation(formField, passwordInput);

  if(passwordConfirmation)
    return {"form": formField, "type": "signup", "password": passwordInput, "passwordConfirmation": passwordConfirmation, "id": loginInput};

  // Sinon on envoie le champs id et celui du password
  if(loginInput)
    return {"form": formField, "type": "login", "password": passwordInput, "id": loginInput};

  return {"type": false};
}

function auto_login(login_form, login_id){
  // On rempli les champs id / password avec les valeurs connues
  login_form["password"].setAttribute("value", login_id["password"]);
  login_form["id"].setAttribute("value", login_id["id"]);
}

function auto_signup(signup_form){
  // On rempli les champs de password avec un aléatoire
  var password = newPassword();
  signup_form["password"].setAttribute("value", password);
  signup_form["passwordConfirmation"].setAttribute("value", password);
}


function ecouter(form){
  // On intercepte
  form["form"].addEventListener('submit', e => {
    // On récupère les id
    var form = find_password_form();
    var password = form["password"].value;
    var id = form["id"].value;

    // On compare à ceux de la bdd

    // On demande si on peut les retenir
    if(!confirm("Enregistrer dans Safe Chest ?", null, "btn-success", "Oui"))
      return true;

    // On les retient

    return true;
  });
}


function get_site(){
  return window.location.host;
}


/* On gère le tout */
function main(){
  var form = find_password_form();

  // Connexion, on rempli si possible, sinon on intercepte
  if(form["type"] == "login"){
    login_id = {"id": "Mathieu", "password": "p4ssw0rd"};
    auto_login(form, login_id);
    // On écoute l'envoie du formulaire pour enregistrer id et mdp si changement
    ecouter(form);
  }
  // Inscription, on prérempli
  if(form["type"] == "signup"){
    auto_signup(form);
    ecouter(form);
    // On écoute l'envoie du formulaire pour enregistrer id et mdp
  }
}
main();