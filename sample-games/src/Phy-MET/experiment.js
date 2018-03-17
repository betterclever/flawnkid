//A MAGNETIC-EFFECT-TESTER
/*    
        Developer : SIDDHARTH - ACHARYA
        COLLEGE : IIIT - Allahabad
*/
var mySceneTLX;        
var mySceneTLY;        
var mySceneBRX;        
var mySceneBRY;        
var mySceneW;          
var mySceneH;          
var myCenterX;         
var myCenterY;         

var wallThickness;
var myBack;             
    
var line;
var line1;
var line1a;
var line2;
var line3;
var line4;
var line5;
var line6;

var dir;
var aro;
var aro1;

var sl;

var open;

var f;
var c;
var u;

var dirR;
var aroR;
var aroR1;
var f1;
var c1;
var u1;

var voltage;
var voltageV = 10;
var voltage2;
var voltageV2 = 10;

var GS;
var GSF;

var BatteryValue;

var totalVoltage;
var totalCurrent;
var totalCurrentL;
var totalVoltageL;
var deflectionL;
var deflection;
var totalRes;
var totalResL;

var Qq;
var button1;
var inputF;
var inputF1;
var inputF2;
var inputF3;
var A1;
var A2;
var A3;
var A4;
var CA;
var WA;
var NextQuestionButton;
var ExperimentButton;
var needle;
var needle1;

var line3a;
var battery;
var battery1;
var batteryT;
var battery2;
var battery2b;
var battery2T;
var resistor;
var res;
var L;
var L1;
var L2;
var L3;
var L4;
var switchB;
var D1;
var D2;
var Magnet;
var compass;
var innerCircle;
var wire;
var wire1;
var wire2;
var REStext;
var REStext1;
var REStext2;
var REStext1a;
var REStext2a;
var Bulb;
var BulbH;
var BulbB;

var Glo = 0;

var score = 0;
function handleV(newValue) {
    voltageV = newValue;
}
function handleV2(newValue) {
    voltageV2 = newValue;
}
function initialiseControlVariables()
{
    voltage = "Cell_1Voltage(V)"; //2
    voltageV = 10;
    Vstep = 1;

    voltage2 = "Cell_2-Voltage(V)"; //2
    voltageV2 = 10;

    totalVoltageL = "Total_Voltage(V)";
    totalVoltage = voltageV + voltageV2;

    totalCurrentL = "Circuit_Current(A)"
    totalCurrent = 0.25;

    deflectionL = "Deflection(rad)";
    deflection = 0.25;

    totalResL = "Circuit_Res(Ohm)";
    totalRes = 3.5;
}
function initialiseControls()
{
    initialiseControlVariables();
    PIEaddInputSlider(voltage, voltageV, handleV, -8, 10.0, Vstep);
    PIEaddInputSlider(voltage2, voltageV2, handleV2, -8, 10.0, Vstep);
    
    PIEaddDisplayText(totalVoltageL, totalVoltage);
    PIEaddDisplayText(totalCurrentL, totalCurrent);
    PIEaddDisplayText(deflectionL, deflection);
    PIEaddDisplayText(totalResL, totalRes);
}

var helpContent;
function initialiseHelp()
{
    helpContent="";
    helpContent = helpContent + "<h2>A Magnetic effect tester experiment help</h2>";
    helpContent = helpContent + "<h3>About the experiment</h3>";
    helpContent = helpContent + "<p>The experiment shows a electric circuit which demonstrates Magnetic effect as testers , magnetic compass needle shows deflection on it based upon the current strength and its direction .</p>";
    helpContent = helpContent + "<h3>Animation control</h3>";
    helpContent = helpContent + "<p>The top line has animation controls. There are two states of the experiment.</p>";
    helpContent = helpContent + "<h3>The setup stage</h3>";
    helpContent = helpContent + "<p>The initial state is setup stage. In this stage, you can see a control window at the right. You have access to two sliders from it.</p>";
    helpContent = helpContent + "<ul>";
    helpContent = helpContent + "<li>Cell1_Voltage(V)&nbsp;&nbsp;:&nbsp;Controls voltage of upper battery(Cell1) .</li>";
    helpContent = helpContent + "<li>Cell2_Voltage(V)&nbsp;&nbsp;:&nbsp;Controls voltage of lower battery(Cell2) .</li>";
    helpContent = helpContent + "</ul>";
    helpContent = helpContent + "<p>Once you setup the experiment, you can enter the animation stage by clicking the start button</p>";
    helpContent = helpContent + "<h3>The animation stage</h3>";
    helpContent = helpContent + "<p>In the animation stage, the circuit is now complete and therefore when you switch it ON the magnetic compass needle will show deflection based upon the strength and direction of current.</p>";
    helpContent = helpContent + "<p>The right hand panel now shows the values of combined effective voltage produced by the 2 Cells in the circuit , total current in the circuit , deflection produced in the magnetic compass and circuit resistance .</p>";
    helpContent = helpContent + "<p>You can pause and resume the animation by using the pause/play nutton on the top line</p>";
    helpContent = helpContent + "<h2>Happy Experimenting</h2>";
    PIEupdateHelp(helpContent);
}

var infoContent;
function initialiseInfo()
{
    infoContent =  "";
    infoContent = infoContent + "<h2>A Magnetic effect tester experiment concepts</h2>";
    infoContent = infoContent + "<h3>About the experiment</h3>";
    infoContent = infoContent + "<p>The experiment shows a circuit which uses Magnetic effect as a tester , the deflection in the magnetic compass changes with direction and strength of current .</p>";
    infoContent = infoContent + "<h3>Magnetic effect as testers.</h3>";
    infoContent = infoContent + "<p>When the circuit is turned ON as there is current in the circuit therefore compass will show deflection in it which shows that the current is flowing in the circuit.</p>";
    infoContent = infoContent + "<h3>Higher current produces higher deflection in the circuit.</h3>";
    infoContent = infoContent + "<p> Deflection of the magnetic compass will be higher when there is higher value of current in the circuit.</p>";
    infoContent = infoContent + "<h3>Direction of needle changes with changing the direction of current.</h3>";
    infoContent = infoContent + "<p>As soon as the direction of current is reversed in the circuit , the direction of magnetic needle also changes.</p>";
    infoContent = infoContent + "<p>which shows that needle deflects in the direction of current and its deflection increases with increase in current of the circuit.</p>";
    infoContent = infoContent + "<h2>Happy Experimenting</h2>";
    PIEupdateInfo(infoContent);
}

function initialiseScene()
{
    mySceneTLX = 0.0;
    mySceneTLY = 3.0;
    mySceneBRX = 4.0;
    mySceneBRY = 0.0;
    mySceneW   = (mySceneBRX - mySceneTLX);
    mySceneH   = (mySceneTLY - mySceneBRY);
    myCenterX  = (mySceneTLX + mySceneBRX) / 2.0;
    myCenterY  = (mySceneTLY + mySceneBRY) / 2.0;
}
function initialiseOtherVariables()
{
    wallThickness = 0.20;
}

function StartEX() {
    Glo = 0;
    Q1();
    button1.style.visibility = "visible";
    line3a.scale.set(1, 1, 1);
    battery.scale.set(1, 1, 1);
    battery1.scale.set(1, 1, 1);
    batteryT.scale.set(1, 1, 1);
    battery2.scale.set(1, 1, 1);
    battery2b.scale.set(1, 1, 1);
    battery2T.scale.set(1, 1, 1);
    resistor.scale.set(1, 1, 1);
    res.scale.set(1, 1, 1);
    L.scale.set(1, 1, 1);
    L1.scale.set(1, 1, 1);
    L2.scale.set(1, 1, 1);
    L3.scale.set(1, 1, 1);
    L4.scale.set(1, 1, 1);
    switchB.scale.set(1, 1, 1);
    D1.scale.set(1, 1, 1);
    D2.scale.set(1, 1, 1);
    Magnet.scale.set(1, 1, 1);
    compass.scale.set(1, 1, 1);
    innerCircle.scale.set(1, 1, 1);
    wire.scale.set(1, 1, 1);
    wire1.scale.set(1, 1, 1);
    wire2.scale.set(1, 1, 1);
    REStext.style.visibility = "visible";
    REStext1.style.visibility = "visible";
    REStext2.style.visibility = "visible";
    REStext1a.style.visibility = "visible";
    REStext2a.style.visibility = "visible";
    Bulb.scale.set(1, 1, 1);
    BulbH.scale.set(1, 1, 1);
    BulbB.scale.set(1, 1, 1);
    needle.scale.set(1, 1, 1);
    needle1.scale.set(1, 1, 1);
    line.scale.set(1, 1, 1);
    line1.scale.set(1, 1, 1);
    line2.scale.set(1, 1, 1);
    line1a.scale.set(1, 1, 1);
    sl.scale.set(1, 1, 1);
    GS.style.visibility = "visible";
    GSF.style.visibility = "hidden";
    aro.scale.set(0, 0, 0);
    dir.scale.set(0, 0, 0);
    aro1.scale.set(0, 0, 0);
    aroR1.scale.set(0, 0, 0);
    aroR.scale.set(0, 0, 0);
    dirR.scale.set(0, 0, 0);
    PIErender();
}
function TesterQuiz() {
    PIEresetExperiment();
}
function Q4() {
    
}
function Q3() {

}
function Q2() {
    
}
function Q1() {

}
function correctA() {
    
}
function wrongA() {
  
}

function loadExperimentElements()
{
    var geo;
    var geometry;
    var material;

    var bb7 = "font-family: Monospace; color: #000000; margin: 0px; overflow: hidden; ";
    var q = "font-family: Monospace; color: #000000; margin: 0px; overflow: hidden; ";
    var SQBS = "font-family: Monospace; color: #ffffff; margin: 0px; overflow: hidden; font-size: 20px; padding: 8px 16px; border-radius: 8px;background-color: #555555;";
    button1 = document.createElement("button"); button1.setAttribute("id", "StartQuiz");

    PIEsetExperimentTitle("A Magnetic Effect Tester");
    PIEsetDeveloperName("Siddharth Acharya");
    PIEhideControlElement();

    /* initialise help and info content */
    initialiseHelp();
    initialiseInfo();
    
    initialiseScene();

    initialiseOtherVariables();

    line = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 1.0), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    line.position.set(0.5 , 1.5 , 0.0);
    line.castShadow = true;
    PIEaddElement(line);

    line1 = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.52), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    line1.position.set(3.5, 1.25 , 0.0);
    line1.castShadow = true;
    PIEaddElement(line1);

    line1a = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.28), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    line1a.position.set(3.5, 1.87, 0.0);
    line1a.castShadow = true;
    PIEaddElement(line1a);

    line2 = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 3.0), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    line2.position.set(2, 1 , 0.0);
    line2.castShadow = true;
    line2.rotateZ(Math.PI / 2);
    PIEaddElement(line2);

    line3a = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 3), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    line3a.position.set(2, 2, 0.0);
    line3a.rotateZ(Math.PI / 2);
    PIEaddElement(line3a);
    line3a.castShadow = false;
    //line3a.receiveShadow = false;

    
    geometry = new THREE.CylinderGeometry(0.1, 0.1, 0.10);
    material = new THREE.MeshLambertMaterial({ color: 0x000000 });
    battery = new THREE.Mesh(geometry, material);
    battery.position.set(0.53, 1.82 , 0.1);
    PIEaddElement(battery);
    battery.castShadow = false;

    
    geometry = new THREE.CylinderGeometry(0.10, 0.10, 0.20);
    material = new THREE.MeshLambertMaterial({ color: 0xffffff });
    battery1 = new THREE.Mesh(geometry, material);
    battery1.position.set(0.53, 1.67 , 0.1);
    PIEaddElement(battery1);
    battery1.castShadow = false;

    
    geometry = new THREE.CylinderGeometry(0.04,0.04, 0.05);
    material = new THREE.MeshLambertMaterial({ color: 0x000000 });
    batteryT = new THREE.Mesh(geometry, material);
    batteryT.position.set(0.53, 1.9 , 0.1);
    PIEaddElement(batteryT);
    batteryT.castShadow = false;

    
    geometry = new THREE.CylinderGeometry(0.1, 0.1, 0.10);
    material = new THREE.MeshLambertMaterial({ color: 0x000000 });
    battery2 = new THREE.Mesh(geometry, material);
    battery2.position.set(0.53, 1.33, 0.1);
    PIEaddElement(battery2);
    battery2.castShadow = false;

    
    geometry = new THREE.CylinderGeometry(0.10, 0.10, 0.20);
    material = new THREE.MeshLambertMaterial({ color: 0xffffff });
    battery2b = new THREE.Mesh(geometry, material);
    battery2b.position.set(0.53, 1.19, 0.1);
    PIEaddElement(battery2b);
    battery2b.castShadow = false;

   
    geometry = new THREE.CylinderGeometry(0.04, 0.04, 0.05);
    material = new THREE.MeshLambertMaterial({ color: 0x000000 });
    battery2T = new THREE.Mesh(geometry, material);
    battery2T.position.set(0.53, 1.4, 0.1);
    PIEaddElement(battery2T);
    battery2T.castShadow = false;

    
    geometry = new THREE.BoxGeometry(0.50 , 0.25, 0.25);
    material = new THREE.MeshLambertMaterial({ color: 0xbfbfbf });
    resistor = new THREE.Mesh(geometry, material);
    resistor.position.set(2 - 0.5, 1 , 0.1);
    resistor.rotateX(Math.PI / 30);
    resistor.rotateY(Math.PI / 20);
    PIEaddElement(resistor);
    resistor.castShadow = false;

    
    res = new THREE.Mesh(new THREE.CylinderGeometry(0.02, 0.02, 0.3), new THREE.MeshLambertMaterial({ color: 0xDAA520 }));
    res.position.set(2-0.5, 1.18, 0.1);
    res.castShadow = true;
    res.rotateZ(Math.PI / 2);
    res.rotateX(Math.PI / 8);
    res.rotateY(Math.PI / 2);
    PIEaddElement(res);
    
    
    L = new THREE.Mesh(new THREE.CylinderGeometry(0.005, 0.005, 0.04), new THREE.MeshLambertMaterial({ color: 0x000033 }));
    L.position.set(2-0.5, 1.18 , 0.12);
    PIEaddElement(L);
    L.castShadow = false;

   
    L1 = new THREE.Mesh(new THREE.CylinderGeometry(0.005, 0.005, 0.04), new THREE.MeshLambertMaterial({ color: 0xff0000 }));
    L1.position.set(2.08-0.5, 1.18 , 0.12);
    PIEaddElement(L1);
    L1.castShadow = false;

    
    L2 = new THREE.Mesh(new THREE.CylinderGeometry(0.005, 0.005, 0.04), new THREE.MeshLambertMaterial({ color: 0x008000 }));
    L2.position.set(1.92-0.5, 1.18 , 0.18);
    PIEaddElement(L2);
    L2.castShadow = false;

    
    L3 = new THREE.Mesh(new THREE.CylinderGeometry(0.005, 0.005, 0.08), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    L3.position.set(1.86-0.5, 1.16 , 0.18);
    PIEaddElement(L3);
    L3.castShadow = false;
    
    
    L4 = new THREE.Mesh(new THREE.CylinderGeometry(0.005, 0.005, 0.08), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    L4.position.set(2.14-0.5, 1.16 , 0.18);
    PIEaddElement(L4);
    L4.castShadow = false;

   
    geometry = new THREE.BoxGeometry(0.40, 0.12, 0.25);
    material = new THREE.MeshLambertMaterial({ color: 0x303030 });
    switchB = new THREE.Mesh(geometry, material);
    switchB.position.set(3.48, 1.62 , 0.1);
    switchB.rotateX(Math.PI / 6);
    PIEaddElement(switchB);
    switchB.castShadow = false;
        
    geometry = new THREE.BoxGeometry(0.1, 0.20, 0);
    material = new THREE.MeshLambertMaterial({ color: 0x707070 });
    sl = new THREE.Mesh(geometry, material);
    sl.position.set(3.405, 1.66 , 0.4);
    sl.rotateZ(Math.PI / 2);
    PIEaddElement(sl);
    sl.castShadow = false;

 
    geometry = new THREE.CircleGeometry(0.02, 32);
    D1 = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x6666cc, opacity: 0.5 }));
    D1.position.set(3.28, 1.702, 0.2);
    PIEaddElement(D1);
    D1.castShadow = false;
    D1.receiveShadow = false;

    
    geometry = new THREE.CircleGeometry(0.02, 32);
    D2 = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x6666cc, opacity: 0.5 }));
    D2.position.set(3.62, 1.64, 0.25);
    PIEaddElement(D2);
    D2.castShadow = false;
    D2.receiveShadow = false;
    
    
    geometry = new THREE.BoxGeometry(0.8, 0.4, 0);
    material = new THREE.MeshLambertMaterial({ color: 0x6666cc });
    Magnet = new THREE.Mesh(geometry, material);
    Magnet.position.set(2, 2, 0.1);
    //Magnet.rotateX(Math.PI / 6);
    //switchB.rotateY(-Math.PI / 20);
    PIEaddElement(Magnet);
    switchB.castShadow = false;

    
    geometry = new THREE.CircleGeometry(0.19, 32);
    compass = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0xa6a6a6, opacity: 0.5 }));
    compass.position.set(2, 2, 0.1);
    PIEaddElement(compass);
    compass.castShadow = false;
    compass.receiveShadow = false;

    
    geometry = new THREE.CircleGeometry(0.022, 32);
    innerCircle = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x000000, opacity: 0.5 }));
    innerCircle.position.set(2, 2, 0.1);
    PIEaddElement(innerCircle);
    innerCircle.castShadow = false;
    innerCircle.receiveShadow = false;

    geometry = new THREE.ConeGeometry(0.02,0.15 , 32);
    needle = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x000000, opacity: 0.5 }));
    needle.position.set(2, 2.06, 0.2);
    PIEaddElement(needle);
    needle.castShadow = false;
    needle.receiveShadow = false;
    //needle.rotateZ(Math.PI / 18 * 5);
    //needle.position.set(2 - 0.013*5, 2.06 - 0.002*5, 0.2);
    
    geometry = new THREE.ConeGeometry(0.02, 0.15, 32);
    needle1 = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x000000, opacity: 0.5 }));
    needle1.position.set(2, 1.91, 0.2);
    PIEaddElement(needle1);
    needle1.castShadow = false;
    needle1.receiveShadow = false;
    needle1.rotateZ(Math.PI);
    //needle1.rotateZ(Math.PI / 18 * 5);
    //needle1.position.set(2 + 0.014*5, 1.92 + 0.002*5, 0.2);

    
    wire = new THREE.Mesh(new THREE.CylinderGeometry(0.006, 0.010, 0.47), new THREE.MeshLambertMaterial({ color: 0xff0000, opacity: 0.5 }));
    wire.position.set(2, 1.99, 0.22);
    wire.rotateZ(-Math.PI / 6);
    PIEaddElement(wire);
    wire.castShadow = false;

    
    wire1 = new THREE.Mesh(new THREE.CylinderGeometry(0.006, 0.010, 0.47), new THREE.MeshLambertMaterial({ color: 0xff0000, opacity: 0.5 }));
    wire1.position.set(2 - 0.25, 1.99, 0.22);
    wire1.rotateZ(-Math.PI / 6);
    PIEaddElement(wire1);
    wire1.castShadow = false;

    
    wire2 = new THREE.Mesh(new THREE.CylinderGeometry(0.006, 0.010, 0.47), new THREE.MeshLambertMaterial({ color: 0xff0000, opacity: 0.5 }));
    wire2.position.set(2 + 0.25, 1.99, 0.22);
    wire2.rotateZ(-Math.PI / 6);
    PIEaddElement(wire2);
    wire2.castShadow = false;

    //var spinner_obj = new THREE.Object3D();
    //THREE.EventDispatcher.call(switchB);
    //switchB.addEventListener( 'mousemove', function (event) { PIEstartAnimation(); } );
    //switchB.addEventListener('click', PIEstartAnimation);
    //switchB.dispatchEvent({ type: 'click' });
   // switchB.callback = function () { PIEstartAnimation(); }
    //var raycaster = new THREE.Raycaster();
    //var mouse = new THREE.Vector2();
    //PIErenderer.domElement.addEventListener("mousedown", onDocumentMouseDown, false); PIErenderer.domElement.addEventListener("mouseup", onDocumentMouseDown, false);
    //PIErender();
    //PIE.addEventListener('mousemove', onDocumentMouseDown, false);

    var bb = "font-family: Monospace ; color: #ffffff; margin: 0px; overflow: hidden;";
    var bb1 = "font-family: Monospace; color: #000000; margin: 0px; overflow: hidden; ";
    var bb2 = "font-family: Monospace; color: #000000; margin: 0px; overflow: hidden;";
    var bb3 = "font-family: Monospace; color: #000000; margin: 0px; overflow: hidden; font-size: 30px;";
    var bb4 = "font-family: Monospace; color: #00e600; margin: 0px; overflow: hidden; ; background-color: #00e600 ;border-style: inset;";
    var bb5 = "font-family: Monospace; color: #ff0000; margin: 0px; overflow: hidden; ; background-color: #ff0000 ;border-style: inset;";
    
    REStext = document.createElement("p"); REStext.style = bb1; document.body.appendChild(REStext);
    // REStext.innerHTML = "<h4>Resistance</h4>";
    REStext.style.position = "absolute";
    REStext.style.left =  47.5 - 8.5 + '%';
    REStext.style.top =  67.5  + '%';
    
    REStext1 = document.createElement("span"); REStext1.style = bb; document.body.appendChild(REStext1);
    // REStext1.innerHTML = "<h2>+</h2>";
    REStext1.style.position = "absolute";
    REStext1.style.left = 25.5 + '%';
    REStext1.style.top = 39.5  + '%';
    
    REStext2 = document.createElement("span"); REStext2.style = bb2; document.body.appendChild(REStext2);
    // REStext2.innerHTML = "<h2>-</h2>";
    REStext2.style.position = "absolute";
    REStext2.style.left = 25.5 + '%';
    REStext2.style.top = 46 + '%';

    REStext1a = document.createElement("span"); REStext1a.style = bb; document.body.appendChild(REStext1a);
    // REStext1a.innerHTML = "<h2>+</h2>";
    REStext1a.style.position = "absolute";
    REStext1a.style.left = 25.5 + '%';
    REStext1a.style.top = 56.3 + '%';

    REStext2a = document.createElement("span"); REStext2a.style = bb2; document.body.appendChild(REStext2a);
    // REStext2a.innerHTML = "<h2>-</h2>";
    REStext2a.style.position = "absolute";
    REStext2a.style.left = 25.5 + '%';
    REStext2a.style.top = 63 + '%';

    BatteryValue = document.createElement("span"); BatteryValue.style = bb3; document.body.appendChild(BatteryValue);
    // BatteryValue.innerHTML = voltageV + "V";
    BatteryValue.style.position = "absolute";
    BatteryValue.style.left = 19 + '%'
    BatteryValue.style.top = 50  + '%';
    BatteryValue.style.visibility = "hidden";

    geometry = new THREE.CylinderGeometry(0.1, 0.1, 0.06);
    material = new THREE.MeshLambertMaterial({ color: 0x111111 });
    BulbH = new THREE.Mesh(geometry, material);
    BulbH.position.set(2.7, 1, 0.0);
    PIEaddElement(BulbH);
    BulbH.castShadow = false;

    geo = new THREE.Shape();
    geo.moveTo(2.8, 1.03);
    geo.quadraticCurveTo( 2.82,1.1 ,2.82 , 1.1);
    geo.quadraticCurveTo(2.7,1.45,2.58,1.1);
    geo.quadraticCurveTo(2.6,1.03,2.6,1.03);
    geometry = new THREE.ShapeGeometry(geo);
    Bulb = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0xa6a6a6 }));
    PIEaddElement(Bulb);

    geo = new THREE.Shape();
    geo.moveTo(2.72, 0.97);
    geo.quadraticCurveTo(2.7,0.89, 2.68, 0.97);
    geometry = new THREE.ShapeGeometry(geo);
    BulbB = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: 0x222222 }));
    PIEaddElement(BulbB);
    
    GS = document.createElement("span"); GS.style = bb4; 
    // document.body.appendChild(GS);
    GS.innerHTML = "ONN";
    GS.style.position = "absolute";
    GS.style.left = 73.1 + '%';
    GS.style.top = 47.2  + '%';
    GS.addEventListener("click", PIEstartAnimation);

    GSF = document.createElement("span"); GSF.style = bb5; 
    // document.body.appendChild(GSF);
    GSF.innerHTML = "OFF";
    GSF.style.position = "absolute";
    GSF.style.left = 73.1 + '%';
    GSF.style.top = 47.2 + '%';
    GSF.addEventListener("click", PIEresetExperiment);

    dir = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.2), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    dir.position.set(0.6, 2 , 0.0);
    dir.rotateZ(Math.PI / 2);
    PIEaddElement(dir);
    dir.castShadow = false;

    aro = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.05), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    aro.position.set(0.7, 2.01 , 0.0);
    aro.rotateZ(Math.PI / 4);
    PIEaddElement(aro);
    aro.castShadow = false;

    aro1 = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.05), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    aro1.position.set(0.7, 1.99 , 0.0);
    aro1.rotateZ(-Math.PI / 4);
    PIEaddElement(aro1);
    aro1.castShadow = false;

    f = 0;
    c = 0;
    u = 0;


    dirR = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.2), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    dirR.position.set(0.6, 1, 0.0);
    dirR.rotateZ(Math.PI / 2);
    PIEaddElement(dirR);
    dirR.castShadow = false;

    aroR = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.05), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    aroR.position.set(0.7, 1.01, 0.0);
    aroR.rotateZ(Math.PI / 4);
    PIEaddElement(aroR);
    aroR.castShadow = false;

    aroR1 = new THREE.Mesh(new THREE.CylinderGeometry(0.01, 0.01, 0.05), new THREE.MeshLambertMaterial({ color: 0x000000 }));
    aroR1.position.set(0.7, 0.99, 0.0);
    aroR1.rotateZ(-Math.PI / 4);
    PIEaddElement(aroR1);
    aroR1.castShadow = false;

    f1 = 0;
    c1 = 0;
    u1 = 0;

    /*Background of Led-Test*/
    geometry = new THREE.BoxGeometry(mySceneW * 2, mySceneH *2, wallThickness);
    material = new THREE.MeshLambertMaterial({ color: 0xFDF6D5 });
    myBack = new THREE.Mesh(geometry, material);
    myBack.position.set(myCenterX, myCenterY, -1.5 * wallThickness);
    PIEaddElement(myBack);
    myBack.receiveShadow = false;

    initialiseControls();

    PIEstopButton.addEventListener("click", PIEresetExperiment);

    /* Reset all positions */
    resetExperiment();

    PIEsetAreaOfInterest(mySceneTLX, mySceneTLY, mySceneBRX, mySceneBRY);
    
}
function resetExperiment()
{
    StartEX();

    if (temp == 1) {
        needle.rotateZ(Math.PI / 18 * totalCurrent);
        needle.position.set(2 , 2.06 , 0.2);
        needle1.rotateZ(Math.PI / 18 * totalCurrent);
        needle1.position.set(2, 1.91, 0.2);
        temp = 0;
    }
    if (flag == 1) {
        needle.rotateZ(Math.PI / 18 * totalCurrent);
        needle1.rotateZ(Math.PI / 18 * totalCurrent);
        needle.position.set(2, 2.06, 0.2);
        needle1.position.set(2, 1.91, 0.2);
        flag = 0;
    }
    Bulb.material.color.setHex(0xa6a6a6);
    sl.material.color.setHex(0x707070);
    GS.innerHTML = "ONN";
    GSF.innerHTML = "OFF";
    GS.style.visibility = "visible";
    GSF.style.visibility = "hidden";

    initialiseOtherVariables();

    dir.scale.set(0, 0, 0);
    aro.scale.set(0, 0, 0);
    aro1.scale.set(0, 0, 0);

    dirR.scale.set(0, 0, 0);
    aroR.scale.set(0, 0, 0);
    aroR1.scale.set(0, 0, 0);

    voltageV = 10;
    voltageV2 = 10;
    totalCurrent = 0;
    
    BatteryValue.innerHTML = voltageV + "V";
    PIEchangeInputSlider(voltage, voltageV);
    PIEchangeInputSlider(voltage2, voltageV2);

    if (dir.position.x == 3.5) {
        dir.rotateZ(Math.PI / 2);
        aro.rotateZ(- Math.PI / 2);
        aro1.rotateZ(Math.PI / 2);
        f = 0;
    }
    else if (dir.position.y == 1 ) {
        dir.rotateZ(Math.PI );
        aro.rotateZ(Math.PI);
        aro1.rotateZ(Math.PI);
        f = 0;
        c = 0;
    }
    else if (dir.position.x == 0.5) {
        dir.rotateZ(Math.PI / 2);
        aro.rotateZ(-Math.PI/2);
        aro1.rotateZ(-Math.PI/2); 
        f = 0;
        c = 0;
        u = 0;
    }
    dir.position.set(0.6, 2, 0.0);
    aro.position.set(0.7, 2.01, 0.0);
    aro1.position.set(0.7, 1.99, 0.0);

    if (dirR.position.x == 3.5) {
        dirR.rotateZ(Math.PI / 2);
        aroR.rotateZ(-Math.PI / 2);
        aroR1.rotateZ(-Math.PI / 2);
        f1 = 0;
    }
    else if (dirR.position.y == 2) {
        dirR.rotateZ(Math.PI);
        aroR.rotateZ(Math.PI);
        aroR1.rotateZ(Math.PI);
        f1 = 0;
        c1 = 0;
    }
    else if (dirR.position.x == 0.5) {
        dirR.rotateZ(Math.PI / 2);
        aroR.rotateZ(-Math.PI / 2);
        aroR1.rotateZ(Math.PI / 2);
        f1 = 0;
        c1 = 0;
        u1 = 0;
    }
    dirR.position.set(0.6, 1, 0.0);
    aroR.position.set(0.7, 1.01, 0.0);
    aroR1.position.set(0.7, 0.99, 0.0);

}

var temp = 0;
var flag = 0;
var type = 0;
function updateExperimentElements(t, dt)
{
    if (Glo == !1) {

        //sl.material.color.setHex(0x000000);
        GS.style.visibility = "hidden";
        GSF.style.visibility = "visible";
        GS.innerHTML = "ONN";
        GSF.innerHTML = "OFF";

        BatteryValue.innerHTML = voltageV + "V";

        totalVoltage = voltageV + voltageV2;
        if (totalVoltage >= 1) {

            totalCurrent = totalVoltage / 3.3;
            if (totalCurrent <= 2.5)
                Bulb.material.color.setHex(0xffff33);
            else if (totalCurrent > 2.5 && totalCurrent <= 3.5)
                Bulb.material.color.setHex(0xffff1a);
            else
                Bulb.material.color.setHex(0xffff00);
            if (temp == 0) {
                /*Magnetic needle rotation*/
                needle.rotateZ(-Math.PI / 18 * totalCurrent);
                needle.position.set(2 + 0.011 * totalCurrent, 2.06 - 0.005 * totalCurrent, 0.2);
                needle1.rotateZ(-Math.PI / 18 * totalCurrent);
                needle1.position.set(2 - 0.011 * totalCurrent, 1.91 + 0.0071 * totalCurrent, 0.2);
                temp = 1;
            }

            dir.scale.set(1, 1, 1);
            aro.scale.set(1, 1, 1);
            aro1.scale.set(1, 1, 1);

            if (aro.position.x < 3.5 && c != 1) {
                aro.position.set(aro.position.x + 0.02, aro.position.y, aro.position.z);
                aro1.position.set(aro1.position.x + 0.02, aro1.position.y, aro1.position.z);
                dir.position.set(dir.position.x + 0.02, dir.position.y, dir.position.z);
            }
            else if (aro.position.x >= 3.5 && f != 1) {

                aro.position.set(3.5 + 0.01, 1.8, 0);
                aro.rotateZ(Math.PI / 2);
                aro1.position.set(3.5 - 0.01, 1.8, 0);
                aro1.rotateZ(-Math.PI / 2);
                dir.position.set(3.5, 1.9, 0);
                dir.rotateZ(Math.PI / 2);
                f = 1;
            }
            else if (f == 1 && aro.position.y > 1 && u == 0) {
                aro.position.set(aro.position.x, aro.position.y - 0.02, aro.position.z);
                aro1.position.set(aro1.position.x, aro1.position.y - 0.02, aro1.position.z);
                dir.position.set(dir.position.x, dir.position.y - 0.02, dir.position.z);
            }
            else if (aro.position.y <= 1 && c != 1) {
                aro.position.set(3.3, 1 - 0.01, 0);
                aro.rotateZ(-Math.PI / 2);
                aro1.position.set(3.3, 1 + 0.01, 0);
                aro1.rotateZ(+Math.PI / 2);
                dir.position.set(3.4, 1, 0);
                dir.rotateZ(Math.PI / 2);
                c = 1;
            }
            else if (c == 1 && aro.position.x > 0.5) {
                aro.position.set(aro.position.x - 0.02, aro.position.y, aro.position.z);
                aro1.position.set(aro1.position.x - 0.02, aro1.position.y, aro1.position.z);
                dir.position.set(dir.position.x - 0.02, dir.position.y, dir.position.z);
            }
            else if (aro.position.x <= 0.5 && u != 1) {
                aro.position.set(0.5 - 0.01, 1.2, 0);
                aro.rotateZ(-Math.PI / 2);
                aro1.position.set(0.5 + 0.01, 1.2, 0);
                aro1.rotateZ(+Math.PI / 2);
                dir.position.set(0.5, 1.1, 0);
                dir.rotateZ(Math.PI / 2);
                u = 1;
            }
            else if (u == 1) {
                aro.position.set(aro.position.x, aro.position.y + 0.02, aro.position.z);
                aro1.position.set(aro1.position.x, aro1.position.y + 0.02, aro1.position.z);
                dir.position.set(dir.position.x, dir.position.y + 0.02, dir.position.z);
                if (aro.position.y >= 2 - 0.25) {
                    dir.position.set(0.6, 2, 0.0);
                    dir.rotateZ(Math.PI / 2)
                    aro.position.set(0.7, 2.01, 0.0);
                    aro.rotateZ(-Math.PI / 2)
                    aro1.position.set(0.7, 1.99, 0.0);
                    aro1.rotateZ(Math.PI / 2);
                    f = 0;
                    c = 0;
                    u = 0;
                }
            }
        }
        else if (totalVoltage <= -1) {
            totalCurrent = totalVoltage / 4;
            if (totalCurrent >= -2)
                Bulb.material.color.setHex(0xffff1a);
            else if (totalCurrent >= -3)
                Bulb.material.color.setHex(0xffff33);
            else
                Bulb.material.color.setHex(0xffff00);

            if (flag == 0) {
                /*Magnetic needle rotation*/
                needle.rotateZ(-Math.PI / 18 * totalCurrent);
                needle1.rotateZ(-Math.PI / 18 * totalCurrent);

                if (totalCurrent > -3) {
                    needle.position.set(2 + 0.015 * totalCurrent, 2.06 - 0.005 * totalCurrent, 0.2);
                    needle1.position.set(2 - 0.014 * totalCurrent, 1.91 + 0.003 * totalCurrent, 0.2);
                    type = 1;
                }
                else if (totalCurrent == -3) {
                    needle.position.set(2 + 0.015 * totalCurrent, 2.06 - 0.005 * totalCurrent, 0.2);
                    needle1.position.set(2 - 0.014 * totalCurrent, 1.91 + 0.0016 * totalCurrent, 0.2);
                    type = 1;
                }
                else {
                    needle.position.set(2 + 0.013 * totalCurrent, 2.06 + 0.002 * totalCurrent, 0.2);
                    needle1.position.set(2 - 0.014 * totalCurrent, 1.91 - 0.0012 * totalCurrent, 0.2);
                    type = 2;
                }
                flag = 1;
            }

            dirR.scale.set(1, 1, 1);
            aroR.scale.set(1, 1, 1);
            aroR1.scale.set(1, 1, 1);

            if (aroR1.position.x < 3.5 && c1 != 1) {
                aroR.position.set(aroR.position.x + 0.02, aroR.position.y, aroR.position.z);
                aroR1.position.set(aroR1.position.x + 0.02, aroR1.position.y, aroR1.position.z);
                dirR.position.set(dirR.position.x + 0.02, dirR.position.y, dirR.position.z);
            }
            else if (aroR1.position.x >= 3.5 && f1 != 1) {

                aroR.position.set(3.5 - 0.01, 1.2, 0);
                aroR.rotateZ(Math.PI / 2);
                aroR1.position.set(3.5 + 0.01, 1.2, 0);
                aroR1.rotateZ(Math.PI / 2);
                dirR.position.set(3.5, 1.1, 0);
                dirR.rotateZ(Math.PI / 2);
                f1 = 1;
            }
            else if (f1 == 1 && aroR1.position.y < 2 && u1 == 0) {
                aroR.position.set(aroR.position.x, aroR.position.y + 0.02, aroR.position.z);
                aroR1.position.set(aroR1.position.x, aroR1.position.y + 0.02, aroR1.position.z);
                dirR.position.set(dirR.position.x, dirR.position.y + 0.02, dirR.position.z);
            }
            else if (aroR1.position.y >= 2 && c1 != 1) {
                aroR.position.set(3.3, 2 - 0.01, 0);
                aroR.rotateZ(-Math.PI / 2);
                aroR1.position.set(3.3, 2 + 0.01, 0);
                aroR1.rotateZ(+Math.PI / 2);
                dirR.position.set(3.4, 2, 0);
                dirR.rotateZ(Math.PI / 2);
                c1 = 1;
            }
            else if (c1 == 1 && aroR1.position.x > 0.5) {
                aroR.position.set(aroR.position.x - 0.02, aroR.position.y, aroR.position.z);
                aroR1.position.set(aroR1.position.x - 0.02, aroR1.position.y, aroR1.position.z);
                dirR.position.set(dirR.position.x - 0.02, dirR.position.y, dirR.position.z);
            }
            else if (aroR1.position.x <= 0.5 && u1 != 1) {
                aroR.position.set(0.5 + 0.01, 1.8, 0);
                aroR.rotateZ(-Math.PI / 2);
                aroR1.position.set(0.5 - 0.01, 1.8, 0);
                aroR1.rotateZ(+Math.PI / 2);
                dirR.position.set(0.5, 1.9, 0);
                dirR.rotateZ(Math.PI / 2);
                u1 = 1;
            }
            else if (u1 == 1) {
                aroR.position.set(aroR.position.x, aroR.position.y - 0.02, aroR.position.z);
                aroR1.position.set(aroR1.position.x, aroR1.position.y - 0.02, aroR1.position.z);
                dirR.position.set(dirR.position.x, dirR.position.y - 0.02, dirR.position.z);
                if (aroR.position.y <= 1) {
                    dirR.position.set(0.6, 1, 0.0);
                    dirR.rotateZ(Math.PI / 2);
                    aroR.position.set(0.7, 1.01, 0.0);
                    aroR.rotateZ(-Math.PI / 2);
                    aroR1.position.set(0.7, 0.99, 0.0);
                    aroR1.rotateZ(Math.PI / 2);
                    f1 = 0;
                    c1 = 0;
                    u1 = 0;
                }
            }
        }
        else {
            dir.scale.set(0, 0, 0);
            aro.scale.set(0, 0, 0);
            aro1.scale.set(0, 0, 0);
        }

    }

    PIEchangeDisplayText(totalVoltageL, totalVoltage);
    PIEchangeDisplayText(totalCurrentL, totalCurrent);
    PIEchangeDisplayText(deflectionL, -Math.PI / 18 * totalCurrent);
}