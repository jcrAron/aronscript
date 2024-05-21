error = catcher{
    1+2+3;
    throw "throw!!!!";
}
if(error != null){
    return "return error: " + error;
}
loop_1: for(i=0;i<40;i+=1){
    if_label: if(i>=30){
        break loop_1;
    }
    if_label: if(i % 3 == 0 || i % 7 == 0){
        env[i] = true;
    }else {
        env[-i] = i % 3 == 0 || i % 7 == 0;
    }
}
newFunction = function(true,false,1+2+-6,a=10,b=20,c=30*60,i=i,[i]="hello"){
    return env[0];
}

return (env[23] != null && env[23]) || !env[-23] && newFunction() ;