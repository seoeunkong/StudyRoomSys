

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel{

int b2, b1, now;

public void paint (Graphics g){

g.clearRect(0, 0, getWidth(), getHeight());
g.drawLine(50,250,350,250);
for (int cnt = 1; cnt <11; cnt++) {

g.drawString(cnt * 10 + "", 25, 255-20 *cnt);
g.drawLine(50, 250-20 *cnt, 350,250-20*cnt);

}


Calendar mon = Calendar.getInstance();

g.drawLine(50,20,50,250);
mon.add(Calendar.MONTH , -2);
g.drawString(new java.text.SimpleDateFormat("yyyy-MM").format(mon.getTime()),100,270);

mon.add(Calendar.MONTH , 1);
g.drawString(new java.text.SimpleDateFormat("yyyy-MM").format(mon.getTime()),200,270);

mon.add(Calendar.MONTH , 1);
g.drawString(new java.text.SimpleDateFormat("yyyy-MM").format(mon.getTime()),300,270);

g.setColor(Color.RED);

if(b1 > 0)

g.fillRect(110,  250-b1 *2, 10, b1*2);

if(b2 > 0)

g.fillRect(210,  250-b2 *2, 10, b2*2);

if(now > 0)

g.fillRect(310,  250-now *2, 10, now*2);

}

void setScores(int b1, int b2, int now) {

this.b1 = b1;
this.b2 = b2;
this.now = now;

}

} 
