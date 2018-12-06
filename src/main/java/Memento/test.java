package Memento;

import java.util.ArrayList;

import players.Player;
import rrrummy.AbleToAddBothSideException;
import rrrummy.InvalidTileException;
import rrrummy.Meld;
import rrrummy.Tile;

public class test {
	public static void main(String[] args) throws AbleToAddBothSideException, InvalidTileException {
		Caretaker caretaker = new Caretaker();
		Originator originator = new Originator();
		ArrayList<Tile> hand = new ArrayList<Tile>();
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		Meld meld = new Meld();
		Tile handt = new Tile("R4");
		hand.add(handt);
		Tile r1 = new Tile("R1");
		Tile r2 = new Tile("R2");
		Tile r3 = new Tile("R3");
		Tile r4 = new Tile("R4");
		meld.add(r1);
		meld.add(r2);
		meld.add(r3);
		meldList.add(meld);
		// ��ʼ��״̬��ʶ "0"
		originator.setState(meldList, hand);
		// ����״̬Ϊ"0"�ı���¼����
		Memento memento_1 = originator.createMemento();
		// ����¼��Originator״̬�ı���¼ ���� Caretaker����¼�����ߴ���
		caretaker.setMemento(memento_1);
		showState(originator);
		
		System.out.println("----- change originator -----");
		// ����ԭ������״̬��ʶΪ"1"
		meld.add(r4);
		hand.remove(0);
		originator.setState(meldList,hand);
		showState(originator);
		
		System.out.println("----- restore originator -----");
		originator.restoreMemento(caretaker.getMemento());
		showState(originator);
	}
	
	private static void showState(Originator originator) {
		System.out.println("Originator current state��" + originator.getPlayerHand() + " " + originator.getTable());
	}
}
