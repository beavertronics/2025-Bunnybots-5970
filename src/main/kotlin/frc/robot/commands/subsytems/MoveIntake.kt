package frc.robot.commands.subsytems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.Intake

class MoveIntake(val state: DoubleSolenoid.Value) : Command() {

    init {
        addRequirements(Intake)
    }

    override fun execute() {
        Intake.setIntakePosition(state)
    }

    override fun isFinished(): Boolean {
        return false
    }
}