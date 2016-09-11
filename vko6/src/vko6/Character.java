/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko6;

/**
 *
 * @author Oski
 */
public class Character {
    WeaponBehavior weapon;
    String name;

    void fight(){
        System.out.println(name + " tappelee aseella " + weapon.name);
    }
}

class Queen extends Character{
    Queen(){
        name = "Queen";
    }
}

class King extends Character{
    King(){
        name = "King";
    }
}

class Knight extends Character{
    Knight(){
        name = "Knight";
    }
}

class Troll extends Character{
    Troll(){
        name = "Troll";
    }
}

class WeaponBehavior{
    String name;
}

class SwordBehavior extends WeaponBehavior{
    SwordBehavior(){
        name = "Sword";
    }
}

class AxeBehavior extends WeaponBehavior{
    AxeBehavior(){
        name = "Axe";
    }
}

class ClubBehavior extends WeaponBehavior{
    ClubBehavior(){
        name = "Club";
    }
}

class KnifeBehavior extends WeaponBehavior{
    KnifeBehavior(){
        name = "Knife";
    }
}


