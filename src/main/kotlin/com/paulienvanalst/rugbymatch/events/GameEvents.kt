package com.paulienvanalst.rugbymatch.events

import com.paulienvanalst.rugbymatch.game.LineOut
import com.paulienvanalst.rugbymatch.game.Scrum
import com.paulienvanalst.rugbymatch.game.SetPiece
import com.paulienvanalst.rugbymatch.team.TeamName
import org.springframework.context.ApplicationEvent

sealed class SetPieceEvent(source: Any, val setPiece: SetPiece, val winningTeam: TeamName) : ApplicationEvent(source)
class ScrumWasPlayed(source: Any, scrum: Scrum, winningTeam: TeamName) : SetPieceEvent(source, scrum, winningTeam)
class LineOutWasPlayed(source: Any, lineOut: LineOut, winningTeam: TeamName) : SetPieceEvent(source, lineOut, winningTeam)

fun List<SetPieceEvent>.wonBy(teamName: TeamName) : List<SetPiece> {
    return this.filter { it.winningTeam == teamName }.map { it.setPiece }
}

fun List<SetPieceEvent>.lostBy(teamName: TeamName) : List<SetPiece> {
    return this.filter { it.winningTeam != teamName }.map { it.setPiece }
}

fun List<SetPieceEvent>.scrumEvents() : List<SetPieceEvent> = this.filter { it is ScrumWasPlayed }
fun List<SetPieceEvent>.lineOutEvents() : List<SetPieceEvent> = this.filter { it is LineOutWasPlayed }