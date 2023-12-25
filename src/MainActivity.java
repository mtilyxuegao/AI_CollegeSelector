
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity {

	public static void main(String[] args) {
		new MainActivity(args);
	}

	WebServerThread thread;
	Button button;
	public MainActivity(String[] args) {
		Frame f = new Frame("AI��ѧ��У����");
		//f.setUndecorated(true); // ȥ��ϵͳ�Դ����ڱ߿�
		//f.setBackground(new Color(255, 255, 255, 128));
		f.setSize(640, 480);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Window w = e.getWindow();
				w.setVisible(false);
				w.dispose(); // �ͷŴ���
				thread.destroy();
			}
		});
		f.setVisible(true);
		Panel p = new Panel();
		f.add(p);
		button = new Button("��˲鿴IP��ַ");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					button.setLabel(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				try {
					Utils.getLocalHostLANAddress();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		p.add(button);
		thread = new WebServerThread();
		thread.start();
	}
}
