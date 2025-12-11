    package mars.mips.instructions.customlangs;

    //import jdk.vm.ci.code.Register;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.io.*;
    import mars.mips.instructions.*;
    import java.util.Random;

public class PvZLanguage extends CustomAssembly{

    @Override
    public String getName(){
        return "PvZ Assembly Language";
    }

    @Override
    public String getDescription() {
        return "Run an assembly langauge program themed Plants Vs Zombies";
    }

    @Override
    protected void populate() {
        instructionList.add(
                new BasicInstruction(
                        "addi $t1,$t2,0",
                        "Add immediate val: $t1 == $t2, + immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "000001 fffff sssss ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int firstReg = operands[0];
                                int secondReg = operands[1];
                                int immediate = operands[2] << 16 >> 16;

                                int newReg = RegisterFile.getValue(secondReg) + immediate;
                                RegisterFile.updateRegister(firstReg, newReg);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "zeq $t1, label",
                        "Branch if zombie is zero",
                        BasicInstructionFormat.I_FORMAT,
                        "100111 fffff sssss ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int check = operands[0];

                                //if (RegisterFile.getValue(check) <= 0)
                            }
                        }));

        instructionList.add( //collects sun
                new BasicInstruction("sunUp $t1,25", //$t1 is the storage and immediate is how much is added
                "Collected sun: increase sun amount ($t1) with immediate value",
                        BasicInstructionFormat.I_FORMAT,
                        "100000 fffff 00000 ssssssssssssssss", //immedaite format
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int value = RegisterFile.getValue(operands[0]);
                                int imm = operands[1] << 16 >> 16;
                                int totalSun = value + imm;
                                RegisterFile.updateRegister(operands[0], totalSun);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("spendSun $t1,100",
                    "Spent sun for plant: Change sun amount ($t1) with an immedaite value.",
                    BasicInstructionFormat.I_FORMAT,
                    "100001 fffff 00000 ssssssssssssssss",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                         int[] operands = statement.getOperands();
                         int value = RegisterFile.getValue(operands[0]);
                         int imm = operands[1] << 16 >> 16;
                         int totalSun = value - imm;
                         RegisterFile.updateRegister(operands[0], totalSun);
                        }
                    }));

        instructionList.add(
                new BasicInstruction("placePlant $t1,1",
                        "Placed Plant: Change $t1 to an ID (immediate value).",
                        BasicInstructionFormat.I_FORMAT,
                        "100100 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int imm = operands[1] << 16 >> 16;
                                RegisterFile.updateRegister(operands[0], imm);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("removePlant $t1",
                        "Removed Plant: Removed plant represented by $t1",
                        BasicInstructionFormat.I_FORMAT,
                        "100101 fffff 00000 0000000000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0], 0);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("damage $t1, 25",
                        "Damage: Plant at $t1 was damaged by 25 HP",
                        BasicInstructionFormat.I_FORMAT,
                        "101000 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int health = RegisterFile.getValue(operands[0]);
                                int damage = operands[1] << 16 >> 16;
                                int newHealth = health - damage;
                                RegisterFile.updateRegister(operands[0], newHealth);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("heal $t1, 100",
                     "Heal: Plant was healed to max HP.",
                     BasicInstructionFormat.I_FORMAT,
                     "101001 fffff 00000 ssssssssssssssss",
                     new SimulationCode() {
                         public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();
                            int health = RegisterFile.getValue(operands[0]);
                            int heal = operands[1] << 16 >> 16;
                            int newHealth = health + heal;
                            RegisterFile.updateRegister(operands[0], newHealth);
                        }
                    }));

        instructionList.add(
                new BasicInstruction("move $t1,1",
                        "Zombie Moved Forward: Zombie moved forward by an immediate number of tiles",
                        BasicInstructionFormat.I_FORMAT,
                        "101100 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int position = RegisterFile.getValue(operands[0]);
                                int tiles = operands[1] << 16 >> 16;
                                int newPosition = position + tiles;
                                RegisterFile.updateRegister(operands[0], newPosition);
                            }
                }));

        instructionList.add(
                new BasicInstruction("knockBack $t1,1",
                        "Pushed: Push zombie back a number of steps",
                        BasicInstructionFormat.I_FORMAT,
                        "101101 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int position = RegisterFile.getValue(operands[0]);
                                int tiles = operands[1] << 16 >> 16;
                                int newPosition = position - tiles;
                                RegisterFile.updateRegister(operands[0], newPosition);
                            }
                }));

        instructionList.add(
                new BasicInstruction(
                        "next $t1, 10",
                        "Next Wave: Next wave",
                        BasicInstructionFormat.I_FORMAT,
                        "111000 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int wave = RegisterFile.getValue(operands[0]);
                                int nextWave = operands[1] << 16 >> 16;
                                int curWave = wave + nextWave;
                                RegisterFile.updateRegister(operands[0], curWave);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("timer $t1",
                        "Timer: Decrement the time in $t1 by 1 sec",
                        BasicInstructionFormat.I_FORMAT,
                        "111001 fffff 00000 0000000000000001",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int time = RegisterFile.getValue(operands[0]);
                                int newTime = time - 1;
                                RegisterFile.updateRegister(operands[0], newTime);
                            }
                        }));

        instructionList.add(
          new BasicInstruction(
                  "shoot $t1,$t2",
                  "Peashooter Shot: Zombie $t2 takes damage from plant $t1.",
                  BasicInstructionFormat.R_FORMAT,
                  "110000 fffff sssss ttttt 00000000000",
                  new SimulationCode() {
                      public void simulate(ProgramStatement statement) throws ProcessingException {
                          int[] operands = statement.getOperands();
                          int plantDmg = RegisterFile.getValue(operands[0]);
                          int health = RegisterFile.getValue(operands[1]);

                          int newHealth = health - plantDmg;
                          if (newHealth < 0) newHealth = 0;
                          RegisterFile.updateRegister(operands[1], newHealth);
                      }
                  }));

        instructionList.add(
            new BasicInstruction(
                    "garlic $t1, $t2",
                    "Garlic: Switches the lane of the zombie from $t1 to $t2",
                    BasicInstructionFormat.R_FORMAT,
                    "101111 fffff sssss ttttt 00000000000",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();
                            int firstLane = RegisterFile.getValue(operands[0]);
                            int secondLane = RegisterFile.getValue(operands[1]);

                            RegisterFile.updateRegister(operands[0], secondLane);
                            RegisterFile.updateRegister(operands[1], firstLane);
                        }
                    }));

        instructionList.add(
                new BasicInstruction(
                        "explode $t1",
                        "Explode: Explodes tile $t1",
                        BasicInstructionFormat.I_FORMAT,
                        "110101 fffff 00000 0000000000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0], 0);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "boost $t1,25",
                        "Boosts Sunflower: Doubles the value of the sun amount in $t1",
                        BasicInstructionFormat.I_FORMAT,
                        "100010 fffff 00000 ssssssssssssssss",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int value  = RegisterFile.getValue(operands[0]);
                                int imm = operands[1] << 16 >> 16;

                                int newValue = value + (imm * 2);
                                RegisterFile.updateRegister(operands[0], newValue);
                            }
                        }));

        instructionList.add(
            new BasicInstruction(
                    "jalapeno,$t1",
                    "Jalapeno: Burn the lane $t1",
                    BasicInstructionFormat.I_FORMAT,
                    "101110 fffff 00000 0000000000000000",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();
                            int burned = -1;
                            RegisterFile.updateRegister(operands[0], burned);
                        }
                    }));

        instructionList.add(
                new BasicInstruction(
                        "hypno $t1",
                        "Hypnoshroom: Hypnotizes target $t1 and targets enemy zombies",
                        BasicInstructionFormat.I_FORMAT,
                        "110100 fffff 00000 0000000000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int friend = 7;
                                RegisterFile.updateRegister(operands[0], friend);
                            }
                        }));

        instructionList.add(
            new BasicInstruction(
                    "chomp $t1, 200",
                    "Chomper: If zombie is regular zombie, zombie is eaten. If not, 200 damage will be inflicted",
                    BasicInstructionFormat.I_FORMAT,
                    "101010 fffff 00000 ssssssssssssssss",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();
                            int health = RegisterFile.getValue(operands[0]);
                            int damage = operands[1] << 16 >> 16;

                            int newHealth;
                            if (health <= damage) newHealth = 0;
                            else newHealth = health - damage;

                            RegisterFile.updateRegister(operands[0], newHealth);
                        }
                    }));

        instructionList.add(
            new BasicInstruction(
                    "wallnut $t1",
                    "Wallnut: High health plant that tanks damage",
                    BasicInstructionFormat.I_FORMAT,
                    "100110 fffff 00000 0000000000000000",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();
                            int wallnutHealth = 1000;
                            RegisterFile.updateRegister(operands[0], wallnutHealth);
                        }
                    }));

        instructionList.add(
                new BasicInstruction(
                        "repeater $t1, $t2",
                        "Repeater: Deal double the damage with repeated shots from a single plant",
                        BasicInstructionFormat.R_FORMAT,
                        "110001 fffff sssss ttttt 00000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int attackDmg = RegisterFile.getValue(operands[0]);
                                int health = RegisterFile.getValue(operands[1]);

                                int newHealth = health - (attackDmg * 2);
                                RegisterFile.updateRegister(operands[1], newHealth);
                            }
                        }));

        instructionList.add(
            new BasicInstruction(
                    "ice $t1,$t2,$t3",
                    "Ice Peashooter: Deals damage to zombie and also freezes the zombie",
                    BasicInstructionFormat.R_FORMAT,
                    "110010 fffff sssss ttttt 00000000000",
                    new SimulationCode() {
                        public void simulate(ProgramStatement statement) throws ProcessingException {
                            int[] operands = statement.getOperands();

                            int damage = RegisterFile.getValue(operands[0]);
                            int health = RegisterFile.getValue(operands[1]);
                            int speed = RegisterFile.getValue(operands[2]);

                            int newHealth = health - damage;
                            if (newHealth < 0 ) newHealth = 0;

                            int newSpeed = 0;

                            RegisterFile.updateRegister(operands[1], newHealth);
                            RegisterFile.updateRegister(operands[2], newSpeed);
                        }
                    }));

        instructionList.add(
                new BasicInstruction(
                        "adv $t0,$t4,$t6",
                        "Automatic Clock: Upgate amount of sun, zombie movement, and time",
                        BasicInstructionFormat.R_FORMAT,
                        "101011 fffff sssss ttttt 00000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();

                                int sun = operands[0];
                                //int health = operands[1];
                                int tile = operands[1];
                                int timer = operands[2];

                                int newTime = RegisterFile.getValue(timer);
                                RegisterFile.updateRegister(timer, newTime - 1);

                                int newTile = RegisterFile.getValue(tile);
                                RegisterFile.updateRegister(tile, newTile + 1);

                                int newSun = RegisterFile.getValue(sun);
                                RegisterFile.updateRegister(sun, newSun + 25);
                            }
                        }));

        instructionList.add(
                new BasicInstruction(
                        "dead $t2",
                        "Dead: Clears register if zombie's health is at 0",
                        BasicInstructionFormat.I_FORMAT,
                        "111010 fffff 00000 0000000000000000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                RegisterFile.updateRegister(operands[0], 0);
                            }
                        }));
    }
}