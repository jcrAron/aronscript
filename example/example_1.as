error = catcher{
    1+2+3;
    // throw "throw!!!!";
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
//
1+2+3+4;
imp = import("example_2.as");
//comment 1
newFunction = // comment 2
function(true,false,1+2+- // comment 3
6,a=0,b=200,c=30*60,i=i,[i]="hello")           
{
    if(a<0){
        throw "in function";
    }
    return env[0] && a+b+c > 2000;
}



result = table();
result.bool = (env[23] != null && env[23]) || !env[-23] && newFunction(a=1) && table(a=1,b=2,c=3).c == 3;
result.myTable = table(tableIndex=30);
result.myfunction = newFunction;

return result; 